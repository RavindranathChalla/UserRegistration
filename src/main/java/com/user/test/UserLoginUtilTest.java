package com.user.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.user.main.util.UserLoginUtil;

public class UserLoginUtilTest {

  private List<String> restrictedUserNamesList;
  UserLoginUtil userLoginUtil = new UserLoginUtil();

  private List<String> testPrepareRestrictList() {
    return restrictedUserNamesList = userLoginUtil.prepareRestrictList();
  }

  @Test
  public void testRestrictedNameValidationAsTrue() {
    String userName = "";
    Boolean flag = userLoginUtil.restrictedNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.TRUE, flag);
  }

  @Test
  public void testRestrictedNameValidationAsFalse() {
    String userName = "ravi";
    Boolean flag = userLoginUtil.restrictedNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.FALSE, flag);
  }

  @Test
  public void testUserNameValidationAsTrue() {
    String userName = "ravi";
    Boolean flag = userLoginUtil.userNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.TRUE, flag);
  }

  @Test
  public void testUserNameValidationAsTrue1() {
    String userName = "";
    Boolean flag = userLoginUtil.userNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.TRUE, flag);
  }

  @Test
  public void testUserNameValidationAsTrue2() {
    String userName = null;
    Boolean flag = userLoginUtil.userNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.TRUE, flag);
  }

  @Test
  public void testUserNameValidationAsFalse() {
    String userName = "ravindranath";
    Boolean flag = userLoginUtil.userNameValidation(userName);
    Assert.notNull(flag);
    assertEquals(Boolean.FALSE, flag);
  }

  @Test
  public void testCheckRestrictedNames() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "abuse";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.TRUE, restrictFlag);
  }

  @Test
  public void testCheckRestrictedNames1() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "cowgrass";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.TRUE, restrictFlag);
  }

  @Test
  public void testCheckRestrictedNames2() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "drunkgrass";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.TRUE, restrictFlag);
  }

  @Test
  public void testCheckRestrictedNames3() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "ravidrunknath";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.TRUE, restrictFlag);
  }

  @Test
  public void testCheckRestrictedNames4() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "ravinathchalla";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.FALSE, restrictFlag);
  }

  @Test
  public void testCheckRestrictedNames5() {
    restrictedUserNamesList = testPrepareRestrictList();
    String userName = "jhon1234";
    Boolean restrictFlag = userLoginUtil.checkRestrictedNames(restrictedUserNamesList, userName);
    Assert.notNull(restrictFlag);
    assertEquals(Boolean.FALSE, restrictFlag);
  }

  private List<String> prepareUserNameList() {
    List<String> usersList = new ArrayList<String>();
    usersList.add("ravindra");
    usersList.add("ravindra1");
    usersList.add("ravindra2");
    usersList.add("daniel123");
    usersList.add("jhon4556");
    usersList.add("indranath");
    return usersList;
  }

  @Test
  public void testGenerateRandomUserNames() {
    List<String> usersList = prepareUserNameList();
    String userName = "ravindra";
    Boolean userflag = Boolean.FALSE;
    Map<Boolean, List<String>> entry =
        userLoginUtil.generateRandomUserNames(userName, usersList, userflag);

    Assert.notNull(entry);
    Assert.notEmpty(entry.get(Boolean.TRUE));
  }

  @Test
  public void testGenerateRandomUserNames1() {
    List<String> usersList = prepareUserNameList();
    String userName = "ravindra123";
    Boolean userflag = Boolean.FALSE;
    Map<Boolean, List<String>> entry =
        userLoginUtil.generateRandomUserNames(userName, usersList, userflag);

    Assert.notNull(entry);
    Assert.isNull(entry.get(Boolean.TRUE));
  }
}
