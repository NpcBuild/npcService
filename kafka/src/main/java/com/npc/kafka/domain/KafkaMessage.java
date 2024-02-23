package com.npc.kafka.domain;

import lombok.Data;

/**
 * @author wow
 */
@Data
public class KafkaMessage {
    private Integer id ;
    private String name ;
    private String message;

    public KafkaMessage() {
    }

    public KafkaMessage(Integer id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }
}
