package org.example.web.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;
import io.netty.util.internal.ObjectUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Date;

/**
 * 用户交易记录
 */
@Data
@Accessors(chain = true)
@TableName("user_trade_record")
public class UserTradeRecord {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("trade_no")
    private String tradeNo;

    @TableField("uid")
    private Long uid;

    @TableField("num")
    private Long num;

    /**
     * 业务来源id
     * @see TradeType
     */
    @TableField("source_id")
    private Integer sourceId;

    /**
     * 操作类型
     * @see OperateType
     */
    @TableField("operate_type")
    private Integer operateType;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public enum OperateType{
        GOLD_ADD(1,"增加金币"),GOLD_DEC(2,"减少金币"),GOLD_CORRECT(3,"冲正金币");
        private Integer code;
        private String desc;

        OperateType(Integer code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Nullable
        public static OperateType from(Integer code){
            return Arrays.stream(OperateType.values()).filter(x-> Objects.equal(x.getCode(),code)).findAny().orElse(null);
        }
    }
    //TODO 这个sourceId可以通过绑定appId进行校验
    public enum TradeType{
        OTHER(0,"其他"),
        Roulette(1,"轮盘"),
        Dice(2,"骰子"),
        Sports(3,"世界杯"),
        Container(4,"Container"),
        Ludo(5,"飞行棋"),
        Lucky_Fruit(6,"幸运水果(三行五列)"),
        Crazy_Marbles(7,"疯狂弹珠"),
        Gold_Egg(8,"猜金蛋"),
        Domino(9,"多米诺"),
        Zodiac(10,"星座"),
        Red_Black(11,"红黑大战"),
        Forest_Party(12,"森林派对");
        private Integer code;
        private String desc;

        TradeType(Integer code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Nullable
        public static TradeType from(Integer code){
            return Arrays.stream(TradeType.values()).filter(x-> Objects.equal(x.getCode(),code)).findAny().orElse(OTHER);
        }
    }
}