package utils.transport;

import junit.framework.TestCase;

/**
 * Created by szpt_user045 on 16.11.2019.
 */
public class DriverCollapseUtilTest extends TestCase {

    public void testCompare() throws Exception {
        String s1 = "C.";
        String s2 = "Clone";
        assertEquals(CollapseUtil.compare(null, s1), s1);
        assertEquals(CollapseUtil.compare(s1, null), s1);
        assertEquals(CollapseUtil.compare(s1, s1), s1);
        assertEquals(CollapseUtil.compare(s1, s2), s2);


    }
}