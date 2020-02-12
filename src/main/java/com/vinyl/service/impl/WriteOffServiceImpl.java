package com.vinyl.service.impl;

import com.vinyl.dao.WriteOffDao;
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
	public List<WriteOff> searchWriteOffs(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
										  List<String> joins, String sorting, String order, Integer limit, Integer offset) {
		String query = QueryBuilder
				.build(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset, WRITE_OFF_TABLE_NAME);
		return writeOffDao.searchWriteOffs(query);
	}

	@Override
	public void update(WriteOff writeOff, Integer writeOffNum) {
		writeOffDao.update(writeOff, writeOffNum);
	}
}
