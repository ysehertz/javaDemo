package com.fuchen.SaleTicketSystem;

/**
 * VIP折扣策略
 * 影院VIP用户除享受票价半价优惠外还可进行积分，积分累计到一定额度可换取电影院赠送的奖品
 */
public class VipDiscountStrategy implements DiscountStrategy {
    
    private static final double VIP_DISCOUNT_RATE = 0.5;
    private static final int POINTS_PER_YUAN = 1; // 每消费1元获得1积分
    
    @Override
    public double calculatePrice(double originalPrice, UserInfo userInfo) {
        if (userInfo.isVip()) {
            double discountedPrice = originalPrice * VIP_DISCOUNT_RATE;
            // 根据折扣后的价格计算积分
            int earnedPoints = (int) discountedPrice * POINTS_PER_YUAN;
            userInfo.addVipPoints(earnedPoints);
            return discountedPrice;
        }
        return originalPrice;
    }
    
    @Override
    public String getDescription() {
        return "VIP折扣：享受半价优惠，同时根据消费金额获得积分奖励";
    }
    
    /**
     * 检查用户是否可以兑换奖品
     * @param userInfo 用户信息
     * @param requiredPoints 兑换所需积分
     * @return 是否可以兑换
     */
    public boolean canRedeemReward(UserInfo userInfo, int requiredPoints) {
        return userInfo.isVip() && userInfo.getVipPoints() >= requiredPoints;
    }
    
    /**
     * 兑换奖品
     * @param userInfo 用户信息
     * @param requiredPoints 兑换所需积分
     * @param rewardName 奖品名称
     * @return 兑换结果信息
     */
    public String redeemReward(UserInfo userInfo, int requiredPoints, String rewardName) {
        if (canRedeemReward(userInfo, requiredPoints)) {
            userInfo.setVipPoints(userInfo.getVipPoints() - requiredPoints);
            return "恭喜您成功兑换了：" + rewardName + "，剩余积分：" + userInfo.getVipPoints();
        } else {
            return "积分不足，无法兑换" + rewardName + "，当前积分：" + userInfo.getVipPoints() + "，需要积分：" + requiredPoints;
        }
    }
}