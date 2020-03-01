package com.vinyl.service.impl;

import com.vinyl.dao.WriteOffDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.WriteOff;
import com.vinyl.service.WriteOffService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class WriteOffServiceImpl implements WriteOffService {

	public static final String WRITE_OFF_TABLE_NAME = "write_off";
	public static final String WRITE_OFF_ALREADY_EXIST_FOR_SUCH_PRODUCT_BARCODE = "Write_off already exist for such product barcode: ";
	@Resource
	private WriteOffDao writeOffDao;

	@Override
	public WriteOff getByNum(Integer num) {
		return writeOffDao.getByNum(num);
	}

	@Override
	public Integer save(WriteOff writeOff) throws AlbumAlreadyExistException {
		String productBarcode = writeOff.getOffRecordBarcode();
		WriteOff alreadyExistingWriteOff = writeOffDao.getByproductBarcode(productBarcode);
		if (nonNull(alreadyExistingWriteOff)) {
			throw new AlbumAlreadyExistException(WRITE_OFF_ALREADY_EXIST_FOR_SUCH_PRODUCT_BARCODE + productBarcode);
		}
		return writeOffDao.save(writeOff);
	}

	@Override
	public List<WriteOff> searchWriteOffs(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWhereParams(), searchDto.getLikeParams(), searchDto.getBetweenParams(),
						searchDto.getJoins(), searchDto.getSorting(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), WRITE_OFF_TABLE_NAME);
		return writeOffDao.searchWriteOffs(query);
	}

	@Override
	public void update(WriteOff writeOff, Integer writeOffNum) {
		writeOffDao.update(writeOff, writeOffNum);
	}
}
