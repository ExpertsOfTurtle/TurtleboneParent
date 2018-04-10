

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.SendHTTPUtil;

public class FetchSamuraiSudoku {
	
	public void fetch(String url) throws Exception {
		String rs = SendHTTPUtil.callApiServer(url, "GET", "", null);
		
		int idx1 = -1, idx2 = -1;
		String str = "";
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			String txt = String.format("rawStartGrids[%d]='", i);
			idx1 = rs.indexOf(txt) + txt.length();
			idx2 = rs.indexOf("'", idx1);
			str = rs.substring(idx1,idx2);
			sb.append(str);
			sb.append("|");
//			System.out.println(str.length() + ":" + str);
		}
		sb.replace(sb.length()-1, sb.length(), "\n");
		IOUtil.writeStringToFile("d:\\rs.txt", sb.toString(), true);
		System.out.println(sb.toString());
	}
	
	@Test
	public void fetchLoop() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		for (int i = 0; i < 500; i++) {
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DATE);
			calendar.add(Calendar.DATE, -1);
			String url = String.format("http://www.sudokucentral.co.uk/samuraisudoku.php?source=%02d%02d%d", day, month, year);
			System.out.println(url);
			fetch(url);
		}
	}
	
	@Test
	public void testString() {
		String str = "1234567";
		StringBuffer sb = new StringBuffer(str);
		sb.replace(str.length()-1, str.length(), "00");
		System.out.println(sb.toString());
	}
}
