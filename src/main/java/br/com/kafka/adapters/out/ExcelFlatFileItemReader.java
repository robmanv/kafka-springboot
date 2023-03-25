package br.com.kafka.adapters.out;


import java.io.IOException;
import java.io.InputStream;

import br.com.kafka.core.entities.Cliente;
import br.com.kafka.core.utils.ProgressBar;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
public class ExcelFlatFileItemReader extends AbstractItemCountingItemStreamItemReader<Cliente>
        implements ResourceAwareItemReaderItemStream<Cliente> {

    private Resource resource;
    private Workbook workbook;
    private Sheet sheet;
    private int rowCount = 0;
    private int rowMaxCount = 0;

    @Override
    public Cliente doRead() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (rowCount < 1 || rowCount > sheet.getLastRowNum()) {
            return null;
        }
        Row row = sheet.getRow(rowCount++);
        Cliente cliente = new Cliente();
        cliente.setId((int) row.getCell(0).getNumericCellValue());
        cliente.setName(row.getCell(1).getStringCellValue());
        cliente.setAge((int) row.getCell(2).getNumericCellValue());
        cliente.setHeight(Double.valueOf(row.getCell(3).getStringCellValue()));
        cliente.setWeight(Double.valueOf(row.getCell(4).getNumericCellValue()));
        cliente.setSalary(Double.valueOf(row.getCell(5).getNumericCellValue()));
        cliente.setAddress(row.getCell(6).getStringCellValue());
        return cliente;
    }

    @Override
    public void doOpen() throws ItemStreamException {
        try {
            InputStream inputStream = resource.getInputStream();
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            rowCount = 1;
            rowMaxCount = sheet.getPhysicalNumberOfRows();
        } catch (IOException e) {
            throw new ItemStreamException("Failed to initialize Excel reader", e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // no-op
    }

    @Override
    public void doClose() throws ItemStreamException {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new ItemStreamException("Failed to close Excel reader", e);
        }
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
