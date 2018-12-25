package com.web.server.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.web.server.core.enumeration.HttpStatus;
import com.web.server.core.enumeration.HttpStatusText;
import com.web.server.utils.ResponseHelper;

/**
 * Created by summer.xia on 2018.12.19
 * 
 * @author summer
 */
public class Response {
	private OutputStream os;
	private HttpStatus status = HttpStatus.S_200;
	private List<String> responseTexts = new ArrayList<>();

	public Response(OutputStream os) {
		this.os = os;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void write(String outContext) {
		responseTexts.add(outContext);
	}

	public void flush() throws IOException {
		if (HttpStatus.S_200 == status) {
			String responseText = ResponseHelper.fmtResponseText(responseTexts);
			if (StringUtils.isBlank(responseText)) {
				responseText = HttpStatusText.of(HttpStatus.S_200).getText();
			}
			os.write(ResponseHelper.fmtResponse(status, responseText).getBytes());
		} else {
			os.write(ResponseHelper.fmtResponse(status, HttpStatusText.of(status).getText()).getBytes());
		}
		responseTexts.clear();
	}
}
