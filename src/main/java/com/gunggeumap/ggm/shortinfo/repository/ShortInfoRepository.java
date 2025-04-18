package com.gunggeumap.ggm.shortinfo.repository;

import com.gunggeumap.ggm.shortinfo.entity.ShortInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortInfoRepository extends JpaRepository<ShortInfo, Long> {

  List<ShortInfo> findAllBy();

}
