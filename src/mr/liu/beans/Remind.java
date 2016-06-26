package mr.liu.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Remind implements Parcelable{
	private Integer id;				//id
	private int     type;			//类型
	private String date;			//时间
	private String desc;			//描述
	private int done;				//是否完成
	private long   repeat;			//重复间隔
	
	public Remind(int type,String date,String desc,int done,long repeat) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.date = date;
		this.desc = desc;
		this.done = done;
		this.repeat = repeat;
	}
	public Remind(int id,int type,String date,String desc,int done,long repeat) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.type = type;
		this.date = date;
		this.desc = desc;
		this.done = done;
		this.repeat = repeat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	
	
	public long getRepeat() {
		return repeat;
	}
	public void setRepeat(long repeat) {
		this.repeat = repeat;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type+"--"+date+"--"+desc+"--"+done;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(type);
		dest.writeString(date);
		dest.writeString(desc);
		dest.writeInt(done);
		dest.writeLong(repeat);
	}
	public static final Parcelable.Creator<Remind> CREATOR = new Creator<Remind>() {

		@Override
		public Remind createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Remind(source);
		}

		@Override
		public Remind[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Remind[size];
		}
	};
	public Remind(Parcel in) {
		type=in.readInt();
		date=in.readString();
		desc=in.readString();
		done=in.readInt();
		repeat=in.readLong();
	}
}
