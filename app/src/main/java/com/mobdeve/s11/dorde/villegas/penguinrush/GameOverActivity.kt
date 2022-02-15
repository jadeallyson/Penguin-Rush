package com.mobdeve.s11.dorde.villegas.penguinrush

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.mobdeve.s11.dorde.villegas.penguinrush.dao.ScoreDAO
import com.mobdeve.s11.dorde.villegas.penguinrush.dao.ScoreDAODatabase
import com.mobdeve.s11.dorde.villegas.penguinrush.databinding.ActivityGameOverBinding
import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score
import com.mobdeve.s11.dorde.villegas.penguinrush.util.SharedPrefUtility
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.ShareButton

class GameOverActivity : AppCompatActivity() {

    private var binding: ActivityGameOverBinding? = null
    private var btnHome: Button? = null
    private var btnTry: Button? = null
    private var tvScore: TextView? = null
    private var tvGotHighScore: TextView? = null
    private var etName: EditText? = null
    private var btnSubmit: Button? = null
    private var btnShare: ShareButton? = null
    private var btnLogin: Button? = null
    private var callbackManager: CallbackManager? = null

    private var finalScore = 0
    private var scoreList: ArrayList<Score?> = ArrayList<Score?>()
    private var scoreAdapter: ScoresAdapter? = null
    lateinit var scoreDAO: ScoreDAO
    var currHighScore: Int = 0
    lateinit var sharedPrefUtility: SharedPrefUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scoreDAO = ScoreDAODatabase(applicationContext)

        FacebookSdk.sdkInitialize(this)

        callbackManager = CallbackManager.Factory.create()


        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        scoreAdapter = ScoresAdapter(applicationContext, scoreList!!)

        btnHome = findViewById(R.id.btn_home)
        btnTry = findViewById(R.id.btn_try)
        tvScore = findViewById(R.id.tv_finalscore)
        tvGotHighScore = findViewById(R.id.tv_gothighscore)
        etName = findViewById(R.id.et_name)
        btnSubmit = findViewById(R.id.btn_submit)
        btnShare = findViewById(R.id.btn_share)
        btnLogin = findViewById(R.id.btn_login)
      


        var bundle = intent.extras
        finalScore = bundle!!.getInt("finalscore")
        tvScore!!.text = finalScore.toString()

        initPrefs()

        if(sharedPrefUtility.getStringPreferences("saved").toString() == "yes"){
            var score = sharedPrefUtility.getStringPreferences("highscore").toString()
            currHighScore = score.toInt()
        }


        //Checks if high score then displays name input TO DO: Check if score is top 10
        if(finalScore > currHighScore){
            tvGotHighScore!!.visibility = View.VISIBLE
            etName!!.visibility = View.VISIBLE
            btnSubmit!!.visibility = View.VISIBLE

            //adds score and username to the database
            binding!!.btnSubmit.setOnClickListener{

                var score = Score(binding!!.etName.text.toString(),
                                  finalScore)

                 scoreDAO.addScore(score)
                 scoreAdapter!!.addScores(scoreDAO.getScores()!!)

                currHighScore = finalScore

                tvGotHighScore!!.visibility = View.INVISIBLE
                etName!!.visibility = View.INVISIBLE
                btnSubmit!!.visibility = View.INVISIBLE
            }

        }

        btnHome!!.setOnClickListener {
            val gotoHomeActivity = Intent(applicationContext, HomeActivity::class.java)

            startActivity(gotoHomeActivity)
            finish()
        }

        btnTry!!.setOnClickListener {
            val gotoGameActivity = Intent(applicationContext, GameActivity::class.java)

            startActivity(gotoGameActivity)
            finish()
        }


    }

    fun initPrefs(){
        sharedPrefUtility = SharedPrefUtility(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        var hashTag = ShareHashtag.Builder().setHashtag("#PenguinRush${finalScore}Points").build()

        var sharecontent = ShareLinkContent.Builder().setQuote("I scored $finalScore in Penguin Rush!")
            .setShareHashtag(hashTag)
            .setContentUrl(Uri.parse("https://www.facebook.com/angelgrace.dorde.27/posts/2091177861048684?notif_id=1644922575774813&notif_t=shared_priv_with_me&ref=notif"))
            .build()

        btnShare!!.shareContent = sharecontent


    }

    override fun onPause(){
        super.onPause()
        sharedPrefUtility.saveStringPreferences("saved", "yes")
        sharedPrefUtility.saveStringPreferences("highscore", "$currHighScore")
    }
}