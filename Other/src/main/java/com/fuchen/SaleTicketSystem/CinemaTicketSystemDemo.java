package com.fuchen.SaleTicketSystem;

/**
 * 影院售票系统演示类
 * 展示策略模式在售票系统中的应用
 */
public class CinemaTicketSystemDemo {
    
    public static void main(String[] args) {
        // 创建售票系统上下文
        TicketSaleContext ticketSystem = new TicketSaleContext();
        
        // 设定电影票原价
        double movieTicketPrice = 50.0;
        
        System.out.println("========== 影院售票系统演示 ==========");
        System.out.println("电影票原价：￥" + movieTicketPrice);
        System.out.println();

        
        // 显示所有可用的折扣策略
        System.out.println("可用的折扣策略：");
        for (String description : ticketSystem.getAllStrategyDescriptions()) {
            System.out.println("- " + description);
        }
        System.out.println();
        
        // 测试场景1：普通成年人
        System.out.println("【测试场景1：普通成年人】");
        UserInfo normalUser = new UserInfo("张三", 30);
        testTicketPurchase(ticketSystem, movieTicketPrice, normalUser);
        
        // 测试场景2：学生
        System.out.println("【测试场景2：学生】");
        UserInfo student = new UserInfo("李四", 20);
        student.setStudent(true);
        testTicketPurchase(ticketSystem, movieTicketPrice, student);
        
        // 测试场景3：儿童（票价满30元）
        System.out.println("【测试场景3：儿童】");
        UserInfo child = new UserInfo("小明", 8);
        testTicketPurchase(ticketSystem, movieTicketPrice, child);
        
        // 测试场景4：儿童（票价不满30元）
        System.out.println("【测试场景4：儿童（票价不满30元）】");
        testTicketPurchase(ticketSystem, 25.0, child);
        
        // 测试场景5：VIP用户
        System.out.println("【测试场景5：VIP用户】");
        UserInfo vipUser = new UserInfo("王五", 35);
        vipUser.setVip(true);
        testTicketPurchase(ticketSystem, movieTicketPrice, vipUser);
        
        // VIP用户再次购票（积分累计）
        System.out.println("【VIP用户再次购票】");
        testTicketPurchase(ticketSystem, movieTicketPrice, vipUser);
        
        // 测试VIP积分兑换
        System.out.println("【VIP积分兑换测试】");
        VipDiscountStrategy vipStrategy = new VipDiscountStrategy();
        System.out.println(vipStrategy.redeemReward(vipUser, 30, "爆米花套餐"));
        System.out.println("兑换后用户信息：" + vipUser);
        System.out.println();
        
        // 测试场景6：军人
        System.out.println("【测试场景6：军人】");
        UserInfo military = new UserInfo("赵六", 28);
        military.setMilitary(true);
        testTicketPurchase(ticketSystem, movieTicketPrice, military);
        
        // 测试场景7：老年人
        System.out.println("【测试场景7：老年人】");
        UserInfo senior = new UserInfo("老李", 65);
        testTicketPurchase(ticketSystem, movieTicketPrice, senior);
        
        // 测试场景8：多重身份（VIP学生）
        System.out.println("【测试场景8：多重身份（VIP学生）】");
        UserInfo vipStudent = new UserInfo("小王", 22);
        vipStudent.setVip(true);
        vipStudent.setStudent(true);
        testTicketPurchase(ticketSystem, movieTicketPrice, vipStudent);
        
        // 测试场景9：多重身份（军人VIP）
        System.out.println("【测试场景9：多重身份（军人VIP）】");
        UserInfo militaryVip = new UserInfo("军官张", 40);
        militaryVip.setMilitary(true);
        militaryVip.setVip(true);
        testTicketPurchase(ticketSystem, movieTicketPrice, militaryVip);
    }
    
    /**
     * 测试购票流程
     * @param ticketSystem 售票系统
     * @param originalPrice 原始票价
     * @param userInfo 用户信息
     */
    private static void testTicketPurchase(TicketSaleContext ticketSystem, 
                                         double originalPrice, UserInfo userInfo) {
        TicketPriceResult result = ticketSystem.calculateFinalPrice(originalPrice, userInfo);
        System.out.println(result);
        System.out.println();
    }
}