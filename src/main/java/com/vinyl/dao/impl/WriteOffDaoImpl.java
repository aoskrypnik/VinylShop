package com.vinyl.dao.impl;

import com.vinyl.dao.WriteOffDao;
import com.vinyl.model.WriteOff;
import com.vinyl.utils.KeyHolderUtils;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
public class WriteOffDaoImpl implements WriteOffDao {

	@Value("${sql.writeoff.create.writeoff.query.path}")
	private String createWriteOffQueryPath;
	@Value("${sql.writeoff.get.writeoff.by.num.query.path}")
	private String getWriteOffByNumQueryPath;
	@Value("${sql.writeoff.get.writeoff.by.product.barcode.query.path}")
	private String getWriteOffByProductBarcodeQueryPath;
	@Value("${sql.writeoff.update.writeoff.query.path}")
	private String updateWriteOffQueryPath;

	@Resource
	private RowMapper<WriteOff> writeOffRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public WriteOff getByNum(Integer num) {
		String getWriteOffByNumQuery = QuerySupplier.getQuery(getWriteOffByNumQueryPath);
		List<WriteOff> writeOffList = jdbcTemplate.query(getWriteOffByNumQuery, new Object[]{num}, writeOffRowMapper);
		return writeOffList.size() == 0 ? null : writeOffList.get(0);
	}

	@Override
	public Integer save(WriteOff writeOff) {
		String createWriteOffQuery = QuerySupplier.getQuery(createWriteOffQueryPath);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(createWriteOffQuery, Statement.RETURN_GENERATED_KEYS);
			if (isNull(writeOff.getOffRecordBarcode())) {
				ps.setNull(1, Types.VARCHAR);
			} else {
				ps.setString(1, writeOff.getOffRecordBarcode());
			}
			ps.setInt(2, writeOff.getSalesmanNum());
			ps.setDate(3, writeOff.getWriteOffDate());
			ps.setNull(4, Types.INTEGER);
			ps.setString(5, writeOff.getReason());
			return ps;
		}, keyHolder);

		return KeyHolderUtils.extractInt(keyHolder, "write_off_num");
	}

	@Transactional
	@Override
	public List<WriteOff> searchWriteOffs(String query) {
		return jdbcTemplate.query(query, writeOffRowMapper);
	}

	@Override
	public void update(WriteOff writeOff, Integer writeOffNum) {
		String updateWriteOffQuery = QuerySupplier.getQuery(updateWriteOffQueryPath);
		Integer fee = writeOff.getFee();
		String reason = writeOff.getReason();
		jdbcTemplate.update(updateWriteOffQuery, fee, reason, writeOffNum);
	}

	@Override
	public WriteOff getByproductBarcode(String barcode) {
		String getWriteOffByProductBarcodeQuery = QuerySupplier.getQuery(getWriteOffByProductBarcodeQueryPath);
		List<WriteOff> writeOffList = jdbcTemplate.query(getWriteOffByProductBarcodeQuery, new Object[]{barcode}, writeOffRowMapper);
		return writeOffList.size() == 0 ? null : writeOffList.get(0);
	}

}
