package org.choongang.admin.banner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BannerGroup extends BaseMember {
    @Id
    private String groupCode;

    @Column(length=80, nullable = false)
    private String groupName; // 배너 그룹명

    private boolean active; // 사용 여부

    @Transient
    private List<Banner> banners;
}
