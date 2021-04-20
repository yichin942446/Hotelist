import android.app.Application
import com.example.assignment.database.reservationDatabase
import com.example.assignment.database.reservationRepository

class WordsApplication : Application() {
    val database by lazy { reservationDatabase.getDatabase(this) }
    val repository by lazy { reservationRepository(database.reservationDao()) }

    //testing new changes
    //I dont care
}