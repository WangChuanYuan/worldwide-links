package org.tze.ruleservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConditionVO {

    private String property;

    private String operator;  // == != > < >= <=

    private Object value;

}
