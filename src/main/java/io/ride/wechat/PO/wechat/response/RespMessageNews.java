package io.ride.wechat.PO.wechat.response;

public class RespMessageNews extends RespMessageBase {
    private Integer ArticleCount;

    private Article[] Articles;

    public RespMessageNews() {

    }

    public RespMessageNews(Integer articleCount, Article[] articles) {
        ArticleCount = articleCount;
        Articles = articles;
    }

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public Article[] getArticles() {
        return Articles;
    }

    public void setArticles(Article[] articles) {
        Articles = articles;
    }
}
