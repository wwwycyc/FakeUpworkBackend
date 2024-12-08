package com.wyc.pojo.VO;

import com.wyc.pojo.Entity.Talent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkInitVO {
    private List<String> partType;
    private List<Talent> talentType;
}
