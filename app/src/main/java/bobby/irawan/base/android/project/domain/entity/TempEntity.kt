package bobby.irawan.base.android.project.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Bobby Irawan on 15/12/20.
 */
@Entity(tableName = "TABLE NAME")
data class TempEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0
) {
    companion object {
        const val COLUMN_ID = "id"
    }
}
