/**
 * @author burcakotlu
 * @date Aug 22, 2014 
 * @time 3:39:41 PM
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 */
public class TestCallingPerlScrit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Process proc =null;
		
		try {
//			proc = Runtime.getRuntime().exec("perl C:\\Users\\burcakotlu\\Downloads\\remap_api.pl batches");
			
//			proc = Runtime.getRuntime().exec("perl C:\\Users\\burcakotlu\\Downloads\\remap_api.pl --mode asm-rsg --from GCF_000001405.13 --dest RefSeqGene --annotation test.bed");
			
//			proc = Runtime.getRuntime().exec("perl C:\\Users\\burcakotlu\\Downloads\\remap_api.pl --mode asm-rsg --from GRC_000001405.13 --dest RefSeqGene --annotation burcak.bed --annot_out burcak_out.bed");

			proc = Runtime.getRuntime().exec("perl C:\\Users\\burcakotlu\\Downloads\\test.pl");
				
			proc.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* dump output stream */
        BufferedReader is = new BufferedReader
            ( new InputStreamReader(proc.getInputStream()));
        String sLine;
        try {
			while ((sLine = is.readLine()) != null) {
			    System.out.println(sLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.flush();
        /* print final result of process */
        System.err.println("Exit status=" + proc.exitValue());
        return;
		

	}

}