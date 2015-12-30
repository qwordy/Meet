package sjtu.se.Util;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class DataProtocol {

	public static class Message {
		public byte type;
		public int total;
		public int length;
		public String msg;
		public String fileName;
		public String remoteDevName;
	}

	public final static byte HEAD = 0xA;
    public final static byte TYPE_END = 0xB;
	public final static byte TYPE_MSG = 0xC;
	public final static byte TYPE_FILE = 0xF;
	public final static byte TYPE_CARD = 0xD;
	public final static byte TYPE_INFO = 0xE;

	public static byte[] packMsg(String msg) throws UnsupportedEncodingException{
		byte[] msgbytes = msg.getBytes("UTF-8");
		byte lowLen = (byte)(msgbytes.length & 0xFF);
		byte hiLen = (byte)(msgbytes.length >> 8 & 0xFF);
		byte[] buf = new byte[msgbytes.length + 4];
		buf[0] = HEAD;
		buf[1] = TYPE_MSG;
		buf[2] = hiLen;
		buf[3] = lowLen;
		System.arraycopy(msgbytes, 0, buf, 4, msgbytes.length);
		return buf;
	}

	//程治谦：所有file类型有BUG，位移时有符号无符号没处理好，由于没用到所以没修改
	public static byte[] packFile(File file) throws UnsupportedEncodingException{
		byte total0 = (byte)(file.length() & 0xFF);
		byte total1 = (byte)(file.length() >> 8  & 0xFF);
		byte total2 = (byte)(file.length() >> 16 & 0xFF);
		byte total3 = (byte)(file.length() >> 24 & 0xFF);
		
		byte[] fnamebytes = file.getName().getBytes("UTF-8");

		byte lowLen = (byte)(fnamebytes.length & 0xFF);
		byte hiLen = (byte)(fnamebytes.length >> 8 & 0xFF);
		
		byte[] buf = new byte[fnamebytes.length + 8];
		buf[0] = HEAD;
		buf[1] = TYPE_FILE;
		buf[2] = total3;
		buf[3] = total2;
		buf[4] = total1;
		buf[5] = total0;
		buf[6] = hiLen;
		buf[7] = lowLen;
		System.arraycopy(fnamebytes, 0, buf, 8, fnamebytes.length);
		return buf;
	}

	public static byte[] packCard(String card) throws UnsupportedEncodingException{
		byte[] msgbytes = card.getBytes("UTF-8");
		byte lowLen = (byte)(msgbytes.length & 0xFF);
		byte hiLen = (byte)(msgbytes.length >> 8 & 0xFF);
		byte[] buf = new byte[msgbytes.length + 4];
		buf[0] = HEAD;
		buf[1] = TYPE_CARD;
		buf[2] = hiLen;
		buf[3] = lowLen;
		System.arraycopy(msgbytes, 0, buf, 4, msgbytes.length);
		return buf;
	}

    public static byte[] packInfo(String info) throws UnsupportedEncodingException{
        byte[] msgbytes = info.getBytes("UTF-8");
        byte lowLen = (byte)(msgbytes.length & 0xFF);
        byte hiLen = (byte)(msgbytes.length >> 8 & 0xFF);
        byte[] buf = new byte[msgbytes.length + 4];
        buf[0] = HEAD;
        buf[1] = TYPE_INFO;
        buf[2] = hiLen;
        buf[3] = lowLen;
        System.arraycopy(msgbytes, 0, buf, 4, msgbytes.length);
        return buf;
    }

	public static Message unpackData(byte[] data) throws UnsupportedEncodingException{
		if(data[0] != HEAD)
			return null;
		Message msg = new Message();
        int lowLen,hiLen;
        switch(data[1]){
		case TYPE_FILE:
			msg.type = TYPE_FILE;
			msg.total = data[2] << 24 | data[3] << 16 | data[4] << 8 | data[5];
			msg.fileName = new String(data, 8, data[6] << 8 | data[7], "UTF-8");
			break;
		case TYPE_MSG:
			msg.type = TYPE_MSG;
            lowLen = data[3] & 0xFF;
            hiLen = data[2] & 0xFF;
            msg.length = hiLen << 8 | lowLen;
			msg.msg = new String(data, 4, msg.length, "UTF-8");
			break;
		case TYPE_CARD:
			msg.type = TYPE_CARD;
            lowLen = data[3] & 0xFF;
            hiLen = data[2] & 0xFF;
            msg.length = hiLen << 8 | lowLen;
			msg.msg = new String(data, 4, msg.length, "UTF-8");
			break;
        case TYPE_INFO:
            msg.type = TYPE_INFO;
            lowLen = data[3] & 0xFF;
            hiLen = data[2] & 0xFF;
            msg.length = hiLen << 8 | lowLen;
            msg.msg = new String(data, 4, msg.length, "UTF-8");
            break;
        case TYPE_END:
            msg.type = TYPE_END;
            break;
		}
		return msg;
	}
}
