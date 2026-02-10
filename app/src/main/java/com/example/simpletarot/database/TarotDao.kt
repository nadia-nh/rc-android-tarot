import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TarotDao {
    @Insert
    suspend fun insertReading(reading: ReadingEntity): Long

    @Insert
    suspend fun insertDrawnCards(cards: List<DrawnCardEntity>)
}
