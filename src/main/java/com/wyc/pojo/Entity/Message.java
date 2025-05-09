package com.wyc.pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private int messageId;
    private String fromUsername;
    private String toUsername;
    private String content;
    private LocalDate latestDate;
}
