package io.ride.service.impl;

import io.ride.service.VoteItemService;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class VoteItemServiceImplTest {

    private VoteItemService itemService = new VoteItemServiceImpl();

    @Test
    public void rankItems() throws SQLException {
        System.out.println(itemService.rankItems(5));

    }
}