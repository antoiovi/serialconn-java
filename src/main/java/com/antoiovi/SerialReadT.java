package com.antoiovi;

public class SerialReadT extends SerialRead {
LineRecived lineRecived;
	public SerialReadT(String iname) throws SerialException {
		super(iname);
 	}
	
	
	/*
	 * 
	 * @param iname
	 * @param irate
	 * @param parityNone
	 * @param idatabits
	 * @param stopbit
	 * @param setRTS
	 * @param setDTR
	 * @throws SerialException
	 */
	public SerialReadT(String iname, int irate, int parityNone, int idatabits, double stopbit, boolean setRTS, boolean setDTR) throws SerialException {

		super(iname, irate,  parityNone,  idatabits,  stopbit,  setRTS, setDTR);
		
	    }
	@Override
	protected void message(char[] chars, int length) {
		String line = String.valueOf(chars);
		lineRecived.setMessage(line);
	}
	public void setLineRecived(LineRecived lineRecived) {
		this.lineRecived = lineRecived;
	}
	
}
