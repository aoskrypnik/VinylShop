package com.vinyl.dao;

import com.vinyl.model.WriteOff;

import java.util.List;

public interface WriteOffDao {

	WriteOff getByNum(Integer num);

	Integer save(WriteOff writeOff);

	List<WriteOff> searchWriteOffs(String query);

	void update(WriteOff writeOff, Integer writeOffNum);

	WriteOff getByproductBarcode(String barcode);
}
