import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "drawn_cards",
    foreignKeys = [
        ForeignKey(
            entity = ReadingEntity::class,
            parentColumns = ["readingId"],
            childColumns = ["readingOwnerId"],
            onDelete = ForeignKey.CASCADE // If reading is deleted, delete these cards too
        )
    ]
)
data class DrawnCardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    val readingOwnerId: Long,
    val name: String,
    val isReversed: Boolean,
    val suit: String,
    val rank: String?
)
