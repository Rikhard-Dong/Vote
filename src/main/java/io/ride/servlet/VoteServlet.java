package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.User;
import io.ride.PO.VoteDetail;
import io.ride.PO.VoteTheme;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VoteDetailService;
import io.ride.service.VoteThemeService;
import io.ride.service.impl.VoteDetailServiceImpl;
import io.ride.service.impl.VoteThemeServiceImpl;
import io.ride.util.CusAccessObjectUtil;
import io.ride.util.DateUtil;
import io.ride.util.JacksonUtil;
import io.ride.wechat.PO.Follow;
import io.ride.wechat.dao.FollowDao;
import io.ride.wechat.service.FollowService;
import io.ride.wechat.service.impl.FollowServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private PrintWriter out;
    private VoteThemeService themeService;
    private VoteDetailService detailService = new VoteDetailServiceImpl();
    private FollowService followService = new FollowServiceImpl();
    private VoteThemeDao themeDao = new VoteThemeDao();

    @Override
    public void init() throws ServletException {
        String basePath = getServletContext().getRealPath("/");
        themeService = new VoteThemeServiceImpl(basePath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out = response.getWriter();

        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "list")) {
                list(request);
            } else if (StringUtils.equals(op, "listUserThemes")) {
                listByUserId(request);
            } else if (StringUtils.equals(op, "delete")) {
                delete(request);
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("未知错误")));
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out = response.getWriter();

        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "start")) {
                StartVote(request);
            } else if (StringUtils.equals(op, "voted")) {
                voted(request);
            }
        } catch (ParseException | SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("未知错误")));
            out.flush();
            e.printStackTrace();
        }
    }

    /**
     * 投票
     *
     * @param request
     */
    private void voted(HttpServletRequest request) throws SQLException {
        SimpleVoteThemeDto themeDto = (SimpleVoteThemeDto) request.getSession().getAttribute("theme");
        VoteTheme theme = themeDao.queryByThemeId(themeDto.getThemeId());
        if (theme == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("错误, 没有选择投票主题")));
            out.flush();
            return;
        }

        String itemIdStr = request.getParameter("itemIds");
        User user = (User) request.getSession().getAttribute("user");
        String ipAddress = CusAccessObjectUtil.getIpAddress(request);
        String openId = request.getParameter("openId");

        if (theme.getIsAnonymous() == 2 && StringUtils.isEmpty(openId)) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("本投票需要关注微信公众号")));
            out.flush();
            return;
        } else if (theme.getIsAnonymous() == 2) {
            Follow follow = followService.getFollow(openId);
            if (follow == null) {
                out.print(JacksonUtil.toJSon(ResultDTO.FAIL("本投票需要关注微信公众号")));
                out.flush();
                return;
            }
        }

        System.out.println("ip address ====> " + ipAddress + ", request.remote ====> " + request.getRemoteAddr());

        int userId = user == null ? 0 : user.getId();
        String[] itemIds = itemIdStr.split("-");
        List<VoteDetail> details = new ArrayList<>();
        for (String itemId : itemIds) {
            details.add(new VoteDetail(Integer.parseInt(itemId), userId, openId, ipAddress));
        }

        System.out.println(details);

        ResultDTO result = detailService.addDetails(details);
        out.print(JacksonUtil.toJSon(result));
        out.flush();

    }

    private void list(HttpServletRequest request) throws SQLException {
        int page = Integer.parseInt(request.getParameter("page"));
        ResultDTO result = themeService.listTheme(page);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    private void StartVote(HttpServletRequest request) throws ParseException, SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登录后操作")));
            out.flush();
            return;
        }

        String theme = request.getParameter("theme");
        String desc = request.getParameter("desc");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Integer ipMax = Integer.valueOf(request.getParameter("ipMax"));
        int isSingle = Integer.parseInt(request.getParameter("isSingle"));
        int isAnonymous = Integer.parseInt(request.getParameter("isAnonymous"));
        int timeDiff = Integer.parseInt(request.getParameter("timeDiff"));
//        int isRestrictedZone = Integer.parseInt(request.getParameter("isRestrictedZone"));

        VoteTheme voteTheme = new VoteTheme(user.getId(), theme, desc, DateUtil.str2Date(startTime), DateUtil.str2Date(endTime),
                isSingle, isAnonymous, timeDiff);
        voteTheme.setTimeDiff(timeDiff);
        voteTheme.setIpMax(ipMax == null ? -1 : ipMax);

        if (isSingle != 0) {
            int maxSelect = Integer.parseInt(request.getParameter("maxSelect"));
            voteTheme.setMaxSelect(maxSelect);
        }
//        if (isRestrictedZone != 0) {
//            String ipZone = request.getParameter("ipZone");
//            voteTheme.setIpZone(ipZone);
//        }

        System.out.println("=====> vote theme servlet start function ======>" + voteTheme);

        out.print(JacksonUtil.toJSon(themeService.addTheme(voteTheme)));
        out.flush();
    }

    private void listByUserId(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登录后操作")));
            out.flush();
            return;
        }
        if (user.getType() == 0) {
            list(request);
            return;
        }

        int page = Integer.parseInt(request.getParameter("page"));

        ResultDTO result = themeService.listThemeByUserId(user.getId(), page);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    private void delete(HttpServletRequest request) throws SQLException {
        int themeId = Integer.parseInt(request.getParameter("themeId"));
        ResultDTO resultDTO = themeService.deleteTheme(themeId);
        out.print(JacksonUtil.toJSon(resultDTO));
        out.flush();
    }
}
