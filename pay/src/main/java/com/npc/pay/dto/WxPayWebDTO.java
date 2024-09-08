package com.npc.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author NPC
 * @description
 * @create 2024/6/22 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxPayWebDTO {
    /**
     * appId
     */
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String package1;
    private String signType;
}
