package com.mhiring.view;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mhiring.pojo.Job;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PdfView extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PdfPTable table = new PdfPTable(6);
		Paragraph p1 = new Paragraph("Jobs created are as follows:");
		Paragraph p2 = new Paragraph(" ");
		
        table.setWidths(new int[]{40, 40, 40, 40, 40, 40});
        table.addCell("Job ID");
        table.addCell("Role Name");
        table.addCell("Skills");
        table.addCell("Location");
        table.addCell("Work Experience");
        table.addCell("Status");
		List<Job> createdJobs = (List<Job>) model.get("createdJobs");
		if(createdJobs != null) {
	        for (Job eachJob : createdJobs){
	            table.addCell(String.valueOf(eachJob.getJobID()));
	            table.addCell(eachJob.getRoleName());
	            table.addCell(eachJob.getDescrp());
	            table.addCell(eachJob.getLocation());
	            table.addCell(eachJob.getExpReq());
	            table.addCell(eachJob.getStatus());
	            
	        }			
		}
		document.add(p1);
		document.add(p2);
		document.add(table);
	}

}
