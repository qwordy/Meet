package sjtu.se.Activity.Search;

import android.bluetooth.BluetoothDevice;
import sjtu.se.UserInformation.Information;

import java.util.Date;

public class DevBluetooth{
    public String Address;
    public String Information;
    public Information Info;
    public Date FoundTime;
    public BluetoothDevice mRemoteDevice;

    public DevBluetooth(String addr, String info, Information i,BluetoothDevice device){
        Address = addr;
        Information = info;
        Info = new Information(i);
        FoundTime = new Date();
        mRemoteDevice = device;
    }

    @Override
    public boolean equals(Object obj) {
        DevBluetooth tmp = (DevBluetooth)obj;
        return (this.Address.equals(tmp.Address) && this.Information.equals(tmp.Information));
    };
};