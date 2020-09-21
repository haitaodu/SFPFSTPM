package com.smartflow.service;

import com.smartflow.dao.MaintenanceItemDao;
import com.smartflow.dto.maintenanceitem.*;
import com.smartflow.model.MaintenanceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：tao
 * @date ：Created in 2020/8/4 16:19

 */

@Service
public class MaintenanceItemeSrviceImpl implements MaintenanceItemService {
    private final
    MaintenanceItemDao maintenanceItemDao;

    @Autowired
    public MaintenanceItemeSrviceImpl(MaintenanceItemDao maintenanceItemDao) {
        this.maintenanceItemDao = maintenanceItemDao;
    }

    @Override
    public List<MaintenanceItemPageDTO> pageDTO(MaintenanceItemSearchDTO maintenanceItemSearchDTO) {
        return maintenanceItemDao.pageDTO
                (maintenanceItemSearchDTO.getPageSize(),
                        maintenanceItemSearchDTO.getPageIndex(),
                        maintenanceItemSearchDTO.getMaintenanceItem());
    }
    @Override
    public MaintenanceItemDetailDTO getDtoById(Integer id) {

        return maintenanceItemDao.getDtoById(id);
    }
    @Override
    public boolean saveEditeData(MaintenanceItemEditeDTO maintenanceItemEditeDTO) {

        return maintenanceItemDao.saveEditeData(maintenanceItemEditeDTO);
    }
    @Override
    public boolean saveAddData(MaintenanceItemAddDTO maintenanceItemAddDTO) {

        return maintenanceItemDao.saveAddData(maintenanceItemAddDTO);
    }
    @Override
    public MaintenanceItemEditeDTO getMaintenanceItemEditeDTO(Integer id) {

        MaintenanceItemEditeDTO maintenanceItemEditeDTO=new MaintenanceItemEditeDTO();
        MaintenanceItem maintenanceItem=maintenanceItemDao.getMaintenanceById(id);
        if (maintenanceItem==null) {
            return null;
        }
        maintenanceItemEditeDTO.setId(maintenanceItem.getId());
        maintenanceItemEditeDTO.setName(maintenanceItem.getName());
        maintenanceItemEditeDTO.setStateId(maintenanceItem.getState());
        maintenanceItemEditeDTO.setTypeId(maintenanceItem.getCategory());
        return maintenanceItemEditeDTO;
    }
    @Override
    public Integer countAll(String maintenanceItemName) {

        return maintenanceItemDao.countAll(maintenanceItemName)
                ;
    }

    @Override
    public boolean delByList(List<Integer> list) {
        for (int id :list)
        {
            maintenanceItemDao.del(id);
        }
        return true;
    }


}
