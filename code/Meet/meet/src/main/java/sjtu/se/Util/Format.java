package sjtu.se.Util;

import sjtu.se.UserInformation.Information;

public class Format {
	
	public static String DoFormat(Information info) {
		
		char dot   =  1;
		String fmt = "";
		
		fmt+=info.baseinfo.Nick+dot+info.keywords+dot;
				
		fmt+=info.baseinfo.Name+dot+info.baseinfo.Gender+dot+info.baseinfo.BirthDay+dot+info.baseinfo.Homeland+dot+info.baseinfo.Location+dot;
		
		fmt+=info.contactinfo.Phone+dot+info.contactinfo.QQ+dot+info.contactinfo.E_Mail+dot;
		
		fmt+=info.edu.College+dot+info.edu.High_School+dot+info.edu.Middle_School+dot+info.edu.Primary_School+dot;
		

		//Debug
		//System.out.println(DoChange(fmt));
		
		return DoChange(fmt);
	}
	
	public static Information DeFormat(String fmt) {
		if (fmt == null)
			return null;
		
		fmt=DeChange(fmt);
		
		String str[] = fmt.split( "" + (char)1 );
		
		if( str.length < 2 ) {
			return null;
		}
		else if( str.length != 2 && !fmt.endsWith( "" + (char)1) ) {
			fmt = "";
			for( int i = 0 ; i < str.length - 1 ; i++ ){
				fmt += str[i] + (char)1;
			}
		}
		else if( str.length == 2 && !fmt.endsWith( "" + (char)1) ){
			if( !fmt.endsWith( ";" ) ){
				String tmp[] = str[1].split( ";" );
				fmt = str[0] + (char)1;
				for( int i = 0 ; i < tmp.length - 1 ; i++ ){
					fmt += tmp[i] + ";";
				}
			}
			fmt += (char)1;
		}
		
		str = fmt.split( "" + (char)1 );
		
		for(int i = 0 ; i < 14 - str.length ; i++)
			fmt += ( (char)1 );
		
		fmt += ".";
		
		//Debug
		//System.out.println(fmt); 
		
		str = fmt.split( "" + (char)1 );
		
		Information info = new Information();
		
		info.baseinfo.Nick		= str[0];
		info.keywords 			= str[1];
		
		info.baseinfo.Name		= str[2];
		info.baseinfo.Gender	= str[3];
		info.baseinfo.BirthDay	= str[4];
		info.baseinfo.Homeland	= str[5];
		info.baseinfo.Location	= str[6];
		
		info.contactinfo.Phone	= str[7];
		info.contactinfo.QQ		= str[8];
		info.contactinfo.E_Mail	= str[9];
		
		info.edu.College		= str[10];
		info.edu.High_School	= str[11];
		info.edu.Middle_School	= str[12];
		info.edu.Primary_School	= str[13];
		
		
		//Debug
		/*for(int i =0;i<str.length;i++){
			System.out.println(str[i] + "   " + i); 
		}*/
	
		return info;
	}
	
	private static String DoSwap(String str) {
    	
    	char charset[] = DoChange(str).toCharArray();
    	
    	int len = charset.length / 4;
    	
    	for( int i = 0 ; i < len ; i += 2 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[3*len-i-1];
    		charset[3*len-i-1]	= tmp;
    	}
    	
    	for( int i = len ; i < 2*len ; i += 2 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[2*len+i];
    		charset[2*len+i] 	= tmp;
    	}
    	
    	for( int i = 1 ; i < 2*len ; i += 4 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[2*len+i];
    		charset[2*len+i] 	= tmp;
    	}
    	
    	str = "";
    	for( int i = charset.length-1 ; i >= 0 ; i-- ) {
    		str += charset[i];
    	}
    	
    	return str;
    }
	
	private static String DeSwap(String str) {
    	
		char charset[] = str.toCharArray();
    	
    	int len = charset.length;
    	
    	for( int i = 0 ; i < len/2 ; i++ ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[len-i-1];
    		charset[len-i-1]	= tmp;
    	}
    	
    	len /= 4;
    	
    	for( int i = 1 ; i < 2*len ; i += 4 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[2*len+i];
    		charset[2*len+i] 	= tmp;
    	}
    	
    	for( int i = 0 ; i < len ; i += 2 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[3*len-i-1];
    		charset[3*len-i-1]	= tmp;
    	}
    	
    	for( int i = len ; i < 2*len ; i += 2 ) {
    		char tmp 			= charset[i];
    		charset[i]			= charset[2*len+i];
    		charset[2*len+i] 	= tmp;
    	}
    	
    	str = "";
    	for( int i = 0 ; i < charset.length ; i++ ) {
    		str += charset[i];
    	}
    	
		return DeChange(str);
    }
    
	private static String DoChange(String str){
		
		byte bytes[] = str.getBytes();
    	
    	for( int i = 0 ; i < bytes.length ; i++ ) {
    		if( ( bytes[i] >> 4 ) == -1 ){
    			byte tmp   = bytes[i+1];
    			bytes[i+1] = (byte)( bytes[i+2] ^ 0x1e ) ;		//0x1e  0001 1110
    			bytes[i+2] = (byte)( tmp ^ 0x27 ) ;				//0x27  0010 0111
    			bytes[i+3] = (byte)( bytes[i+3] ^ 0x3f ) ;		//0x3f  0011 1111
    			i          = i+3;
    		}
    		else if( ( bytes[i] >> 4 ) == -2 ){
    			byte tmp   = bytes[i+1];
    			bytes[i+1] = (byte)( bytes[i+2] ^ 0x2a ) ;		//0x2a  0010 1010
    			bytes[i+2] = (byte)( tmp ^ 0x16 ) ;				//0x16  0001 0110
    			i          = i+2;
    		}
    		else if( ( bytes[i] >> 4 ) == -4 ){
    			bytes[i+1] = (byte)( bytes[i+1] ^ 0x39 ) ;		//0x39  0011 1001
    			i          = i+1;
    		}
    		else{
    			if( bytes[i] % 2 == 0 )
    				bytes[i] = (byte)( bytes[i] ^ 0x5b );		//0x5b  0101 1011
    			else
    				bytes[i] = (byte)( ~bytes[i] ^ 0xec );		//0xec  1110 1100
    		}
    	}
    	
		return new String(bytes);
	}
	
	private static String DeChange(String str){
		
		byte bytes[] = str.getBytes();
    	
    	for( int i = 0 ; i < bytes.length ; i++ ) {
    		if( ( bytes[i] >> 4 ) == -1){
    			byte tmp   = bytes[i+1];
    			bytes[i+1] = (byte)( bytes[i+2] ^ 0x27 ) ;		//0x27  0010 0111
    			bytes[i+2] = (byte)( tmp ^ 0x1e ) ;				//0x1e  0001 1110
    			bytes[i+3] = (byte)( bytes[i+3] ^ 0x3f ) ;		//0x3f  0011 1111
    			i          = i+3;
    		}
    		else if( ( bytes[i] >> 4 ) == -2 ){
    			byte tmp   = bytes[i+1];
    			bytes[i+1] = (byte)( bytes[i+2] ^0x16 ) ;		//0x16  0001 0110
    			bytes[i+2] = (byte)( tmp ^ 0x2a ) ;				//0x2a  0010 1010
    			i          = i+2;
    		}
    		else if( ( bytes[i] >> 4 ) == -4 ){
    			bytes[i+1] = (byte)( bytes[i+1] ^ 0x39 ) ;		//0x39  0011 1001
    			i          = i+1;
    		}
    		else
    			if( bytes[i] % 2 == 0 )
    				bytes[i] = (byte)( ~bytes[i] ^ 0xec );		//0xec  1110 1100
    			else
    				bytes[i] = (byte)( bytes[i] ^ 0x5b );		//0x5b  0101 1011
    	}
    	
    	return new String(bytes);
	}
}
