package org.tze.ruleservice.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RulePO {

    private Long id;

    private String name;
    private String description;

    private Integer projectId;

    private String triggersJson;
    private String actionsJson;

    private LocalDateTime begin;
    private LocalDateTime end;

    private String drl;

    private boolean enabled;
}
