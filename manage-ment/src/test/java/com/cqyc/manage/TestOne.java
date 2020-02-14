package com.cqyc.manage;

import com.cqyc.manage.comm.util.OtherUtil;
import org.junit.Test;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 20-2-5
 */
public class TestOne {

    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
            String s = OtherUtil.sixRandom();
            System.out.println("s = " + s);
        }
    }
}
