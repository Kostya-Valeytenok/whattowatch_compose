package com.raproject.whattowatch.ui.about_content

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.models.ContentInformationModel
import com.raproject.whattowatch.ui.ContentInformation
import com.raproject.whattowatch.ui.theme.WhattowatchTheme

class AboutContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhattowatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentInformation(model = ContentInformationModel(
                        posterUrl = "films/0B.jpg",
                        title = "Inception",
                        year = "2010",
                        genres = "Action, Detective, Drama, Thriller, Fantastic",
                        duration = "108 min"
                    )
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ContentInformation(model = ContentInformationModel(
        posterUrl = "https://kinopoisk-ru.clstorage.net/kz30h1331/ea78b0clH5/4NkNzhXaHu8s5I1MPtpmg1NpJlN5fD1NUMkwLpJh2Odf4sKMo7-g4JYSBybPiQqgF-7o2g-iYf4Jq9So7WpKtaKMH59Ky1HnK7qTA22NNd8cc3AFaWR1HhTUI37MPvfXd9Dr_oWAiMC6c0Fnip5S8EmuoNQE6GvhSuemk6k1FeLj3x7dDcZjywri9V8Y2mWtRi0eWWEbPWF0lSwupWD572z_2vibg6jtnHNwZb_KX99XtpzLGOtlqXTdmJzwJQ3g-dUw1xHHUsU98O1PMfZWtFkOA0NkAj9JQY8hCK9r-uJxw9vNi5qb_6puRWi12VXTdaui7AzOb6hx3L6B7iU93eXcEMsLxnrUDe7SVxfibI9oDAJMegQRaXa3DwqQTd__Xf7uyq27k8i9RXFosP5J7V2HpfcqyFGCa9GUjcIIDvvQyzDBLPFy0wX80VEK_lGXWxAweF40LVxLqA0osWzJy2H6xcSbh7HCikhmWo7xSu5PpqnRBu9vuE7omLbcKif04tM8_x3vVMgmxfBdCfVjg2MeElxJEg54TIIuC4hj4cFE1uLsiYiA4b1QYmay1WD8U5ii6BXfR4tP_5-87jow8ebUG_sm5mbIAMXUWQ3ge4FNLS9ZbCk5XEWIFQSGWOP5VMv806OztPaQWX1fh_hXylCDss8Z-ViVSfqgtPEUC-7_8zH3KsBqygXDz0UH2UqBagYKXmQ_EGRCsAMqjmPnw3HBzvGNu5XAvU5GZLrKQet1qIDVGsp2pVTRqJvwCzfs8NIP2BXQYc8czcZ0DNJGhW0FP11-IB17dKsUDY5M4tla3u_Qj4KV3rlwR1Or3XHMVruB-BvWUYtIwp2gzCsW89DVKOkB5lzoPs7HcRziQLJyPw1Bfzk_fk23ATCYb_zYZPzGx7Owru28ck9Jmftq43eNpe0_9U6mWcO9oNs7L_H3yDfAJe5q4SvS4G063FGOYwIjVGgDLGxokS8fimLb71D4_daAporyvk1dR4zbbetrtJPBNPNhi3LntaXmORbP9O4NzSjkdfQZ8_dUO9ZSl00EA3dDCxJHUaEoNYZ3zvxV3vjqj5GYzLB8Q2i58U7xbLCK0iPPSKN8252V2gQ96sLXNvgC71zGP97xVBDjRolbJCJcfj8sQmeeCi6_d8nZQv7Gy4Cbis-xRE5pq956_USWps0C41epTsCstuMvDe7G9RzaKfhiyiTu9mIK8UCtWQsGZFw1CX1AvSozunfwzXvr38efuqbVsnBQQa7SdeFWh4fDL_VNqFTriqboHh3V98gw4wvefvEv0cVGBfx1gF0NMXFuKytjVIcPMZd3xf9Q_tPGibal8Zh2XnyN4FX2T7GC_jntSpt814-mwCoy0Pf_FOUw-VHnLPDLYDL-b6NbHgBxXho7QUutHjOUQcPQV9PHyqK2o8GFW2Ryj8lTzlOmt9oBz1CdasWwjucNB_7j0TzZJsNW1wr5808O-GOzag0PXHo7HUZvnBQ3n2TB8l_I28uYoYPqh2dEeZbeQM9TsZ_LOsNyqW_EtYjILBn5xt0Q4ijEY9Yix8pZHtZKoWIkPW5vEgtRUpAyBIZ87c5a3MLZmIqy7p1ZfXKE30_ad5GF8R_-aZx58YO-8Aosy9jAMM4P8HfTPsbIWib-S4N7CgJkdTUwTkGcLi6tRfjlcvT81aiZgMS8VnVIj_RI81W1o-0C80ipf-qun90HKtXm8CDjKfFU6in100Ao0kmCUyg-RGMXAGJ9lQkhqn36zG7Vxs2gi4zUhWhzUrnKbup0qIX8H9FaukD5j5vDKDLB3_cK_SP7f_Qg9MRcDflVl0Y_El9dBjxhaaA8Nato6u1-39vomJuv3qNNR2a7wnjcc5SS3iXvRIJ52ra-_C038-H6LOY120b7O9PvWh3XVp9TMz5XZDoAR2C0IR2rdO3LYfL645ywhPWwUEdIu9dN3WGEmfEB0km8YPy1qcM4NPT41g7_Fv9J0zzYznE45UqxfzQhXU8yA25plSoChWHh_ljWw9C4m7Xghk9AXrbIe9hxoKL2F_5Wu3PdqZrDNQ7o7_s-6h3vfc4w4cVmB95SoXEpJHpOJzdva4kzAZda-8FT1cfsiJmF6bp1cFS56VLocYOV0A_ueLlJxJK5_DME3ObCKvcm-GL3Ht_tTQjCcLdJDyl0WhI1cnW9MzCQQNzLbvzx6LmghfOqb0dzmdVT91ORmtQ58mKoUeG_uc81KNL7wgHZH_5R8gPc734W2GaIRwUFV18iHX1rjzoWmlnZ2HHW2tWzhJnytlliW5znePtTuLnoPvB6pnvxiLj8CT_r0tEdzCDQW-8YwdlGM99YhEQVG1d7GBtvZp0NN594z-Z_-M7Fv5W594ZLQ12nxl7xaImp-TTzZL5A8K27xxkPz-fcMNo-xHbTL_jwUzz9a6pOEx1_QBcfXX2pIgG-XcfHQOPt6JyAhsWVZWREsulW7VWiveIbwk6dW9WWjeEONPrk7DDrN8NI6zHy00Eb4WaXXzIGZl8XE01HsjkjsV3B7Ef18carnaTKp2h-X4ThW-hloKH8AdFju3_ciKHqCg_N0fAN_QjLVtQC9ttdM8VPiGkmHW5uKgx-bKARF5dP_cZw5vHOv5qL3qNTZ1OX02jsT6C4-CPCerlO6rO24jM39tDXHusA_1XSB_3sWC76SIVZIilwQRg-RU2hISGXXNz8cdDZ7qOCkdO_TkxXsPta4HCkkf8kzlCbSP-hnP4zB-DuwhbcNuxT7Tj12WQm-lW0YC4sc2owL25ukAori2ba5n78_NC-hLnDgk1AdZvld9VUjpLUPNFEuX_KnKruHjLw1ckv7QDYZNAy8edxCddkp1sLMEFeODheS6s-N41c-OpP5uLyu7G3xqNEZlul6ErIcICf-iXNeJ5K5aupzTI3-8_zAfYC3UfzBfnsfhv9Rp5cNj9kYDESY1aFECSESdjEWefq64Wdisu7SVt9hPd960yQoPcd70iMctg",
        title = "Inception",
        year = "2010",
        genres = "Action, Detective, Drama, Thriller, Fantastic",
        duration = "108 min"
    ))
}