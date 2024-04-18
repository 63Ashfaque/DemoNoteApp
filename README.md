# DemoRoomDB
1.RoomDataBase\
2.DataBinding\
3.RecyclerView\
4.Button Click Events Using Binding
![](https://raw.githubusercontent.com/ashfaque-harrier/DemoRoomDB/master/Screenshot_20230418_125450.png)



## 
```sh
    def room_version = "2.5.1"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    // To use Kotlin annotation processing tool (kapt)
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
```

```sh
@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(contact: Contact):Long

    @Update
    suspend fun updateData(contact: Contact):Int

    @Delete
    suspend fun deleteData(contact: Contact):Int

    @Query("SELECT * FROM contactTable")
    fun getContact():LiveData<List<Contact>>

    @Query("SELECT * FROM contactTable ORDER BY id DESC")
    fun getContactDesc():LiveData<List<Contact>>
}
```


