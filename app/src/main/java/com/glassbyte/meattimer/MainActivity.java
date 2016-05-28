package com.glassbyte.meattimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

//TODO check daylight savings for dialog boxes

public class MainActivity extends AppCompatActivity {
    int mStage = 0;
    NestedGridView mGridView;
    BaseAdapter mBaseAdapter;

    MeatCategories mMeatCategories, mCurrentMeatCategory;
    ChickenCategories mCurrentChickenCategory;
    SteakCategories mCurrentSteakCategory;

    MeatCategory mCategory[], mCurrentChoice[], mChickenCategory[], mSteakCategory[];
    Meat mChickenBBQMeat[], mChickenOvenMeat[], mChickenPanMeat[], mChickenRoastMeat[],
            mBeefMeat[], mFishMeat[], mSteakFilletMeat[], mSteakTBoneMeat[], mSteakStripLoinMeat[],
            mLambMeat[], mTurkeyMeat[], mPigMeat[], mCurrentMeat[];

    Bitmap b_chicken, b_cow, b_fish, b_pig, b_sheep;
    Bitmap b_bbq, b_oven, b_pan, b_roast;
    Bitmap b_strips, b_drumstick, b_null, b_steak, b_beef, b_burger, b_roast_chicken, b_breast;

    Intent mIntent;
    Notify notify = new Notify(MainActivity.this);

    private static final int HEIGHT = 128, WIDTH = 128;

    private enum MeatCategories {
        BEEF, CHICKEN, FISH, LAMB, PIG, STEAK, TURKEY
    }

    private enum ChickenCategories {
        BBQ, OVEN, PAN, ROAST
    }

    private enum SteakCategories {
        TBONE, STRIPLOIN, FILLET
    }

    @Override
    public void onBackPressed() {
        if (mStage > 0) {
            mStage--;
            switch (mStage) {
                case 0:
                    mGridView.setAdapter(new Adapter(this));
                    break;
                default:
                    if (mMeatCategories == MeatCategories.CHICKEN)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.BEEF)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.STEAK)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.FISH)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.TURKEY)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.PIG)
                        mGridView.setAdapter(new Adapter(this));
                    else if (mMeatCategories == MeatCategories.LAMB)
                        mGridView.setAdapter(new Adapter(this));
                    break;
            }
            mGridView.setAdapter(mBaseAdapter);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialiseMeats();

        mBaseAdapter = new Adapter(MainActivity.this);
        mGridView = (NestedGridView) findViewById(R.id.grid_view);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(mBaseAdapter);
        mGridView.setExpanded(true);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                System.out.println(mStage);
                if (mStage == 0) {
                    mCurrentChoice = mCategory;
                    mStage++;
                    switch (position) {
                        case 0:
                            setCurrentMeatCategory(MeatCategories.BEEF);
                            break;
                        case 1:
                            setCurrentMeatCategory(MeatCategories.CHICKEN);
                            break;
                        case 2:
                            setCurrentMeatCategory(MeatCategories.FISH);
                            break;
                        case 3:
                            setCurrentMeatCategory(MeatCategories.LAMB);
                            break;
                        case 4:
                            setCurrentMeatCategory(MeatCategories.PIG);
                            break;
                        case 5:
                            setCurrentMeatCategory(MeatCategories.STEAK);
                            break;
                        case 6:
                            setCurrentMeatCategory(MeatCategories.TURKEY);
                            break;
                    }
                    mGridView.setAdapter(mBaseAdapter);
                } else if (mStage == 1) {
                    if (getCurrentMeatCategory() == MeatCategories.CHICKEN) {
                        switch (position) {
                            case 0:
                                setCurrentChickenCategory(ChickenCategories.BBQ);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                            case 1:
                                setCurrentChickenCategory(ChickenCategories.OVEN);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                            case 2:
                                setCurrentChickenCategory(ChickenCategories.PAN);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                            case 3:
                                setCurrentChickenCategory(ChickenCategories.ROAST);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.BEEF) {
                        mCurrentMeat = mBeefMeat;
                        mGridView.setAdapter(mBaseAdapter);
                        switch (position) {
                            case 0:
                                //BEEF BURGER
                                createAlertDialog(mCurrentMeat[position]);
                                break;
                            case 1:
                                //ROAST BEEF
                                mCurrentMeat[position].setWeight(3000f);

                                MeatDialog meatDialog = new MeatDialog(mCurrentMeat[position].getName());
                                meatDialog.show(getSupportFragmentManager(), "ROAST BEEF");
                                meatDialog.setMeatListener(new MeatDialog.setMeatListener() {
                                    @Override
                                    public void onCookClick(DialogFragment dialogFragment) {
                                        mCurrentMeat[position].setDuration(Timings.getRoastBeefTime(
                                                mCurrentMeat[position].getWeight(), Timings.Doneness.MEDIUM));
                                        launchSetTimer(mCurrentMeat[position]);
                                    }
                                });
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.FISH) {
                        mCurrentMeat = mFishMeat;
                        switch (position) {
                            case 0:
                                //FISH
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.PIG) {
                        mCurrentMeat = mPigMeat;
                        switch (position) {
                            case 0:
                                //ROAST HAM
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.LAMB) {
                        mCurrentMeat = mLambMeat;
                        createAlertDialog(mCurrentMeat[position]);
                        switch (position) {
                            case 0:
                                //CHOPS
                                break;
                            case 1:
                                //LOIN
                                break;
                            case 2:
                                //BURGERS
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.STEAK) {
                        switch (position) {
                            case 0:
                                setCurrentSteakCategory(SteakCategories.FILLET);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                            case 1:
                                setCurrentSteakCategory(SteakCategories.STRIPLOIN);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                            case 2:
                                setCurrentSteakCategory(SteakCategories.TBONE);
                                mStage++;
                                mGridView.setAdapter(mBaseAdapter);
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.TURKEY) {
                        mCurrentMeat = mTurkeyMeat;

                        mCurrentMeat[position].setWeight(4500f);
                        MeatDialog meatDialog = new MeatDialog(mCurrentMeat[position].getName());
                        meatDialog.show(getSupportFragmentManager(), "ROAST TURKEY");
                        meatDialog.setMeatListener(new MeatDialog.setMeatListener() {
                            @Override
                            public void onCookClick(DialogFragment dialogFragment) {
                                mCurrentMeat[position].setDuration(Timings.getRoastTurkeyTime(
                                        mCurrentMeat[position].getWeight()));
                                launchSetTimer(mCurrentMeat[position]);
                            }
                        });
                    }
                    mGridView.setAdapter(mBaseAdapter);
                } else if (mStage == 2) {
                    //either chicken or steak
                    if (getCurrentMeatCategory() == MeatCategories.CHICKEN) {
                        if (getCurrentChickenCategory() == ChickenCategories.BBQ) {
                            mCurrentMeat = mChickenBBQMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //BREAST
                                    break;
                                case 1:
                                    //STRIPS
                                    break;
                                case 2:
                                    //DRUMSTICKS
                                    break;
                                case 3:
                                    //THIGHS
                                    break;
                                case 4:
                                    //WINGS
                                    break;
                            }
                        } else if (getCurrentChickenCategory() == ChickenCategories.OVEN) {
                            mCurrentMeat = mChickenOvenMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //BREAST
                                    break;
                                case 1:
                                    //THIGHS
                                    break;
                                case 2:
                                    //WINGS
                                    break;
                            }
                        } else if (getCurrentChickenCategory() == ChickenCategories.PAN) {
                            mCurrentMeat = mChickenPanMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //BREAST
                                    break;
                            }
                        } else if (getCurrentChickenCategory() == ChickenCategories.ROAST) {
                            mCurrentMeat = mChickenRoastMeat;
                            mCurrentMeat[position].setWeight(1600f);
                            MeatDialog meatDialog = new MeatDialog(mCurrentMeat[position].getName());
                            meatDialog.show(getSupportFragmentManager(), "ROAST CHICKEN");
                            meatDialog.setMeatListener(new MeatDialog.setMeatListener() {
                                @Override
                                public void onCookClick(DialogFragment dialogFragment) {
                                    mCurrentMeat[position].setDuration(Timings.getRoastChickenTime(
                                            mCurrentMeat[position].getWeight()));
                                    launchSetTimer(mCurrentMeat[position]);
                                }
                            });
                            switch (position) {
                                case 0:
                                    //WHOLE CHICKEN
                                    break;
                            }
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.STEAK) {
                        if (getCurrentSteakCategory() == SteakCategories.TBONE) {
                            mCurrentMeat = mSteakTBoneMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //RARE
                                    break;
                                case 1:
                                    //MEDIUM RARE
                                    break;
                                case 2:
                                    //MEDIUM
                                    break;
                                case 3:
                                    //WELL DONE
                                    break;
                            }
                        } else if (getCurrentSteakCategory() == SteakCategories.STRIPLOIN) {
                            mCurrentMeat = mSteakStripLoinMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //RARE
                                    break;
                                case 1:
                                    //MEDIUM RARE
                                    break;
                                case 2:
                                    //MEDIUM
                                    break;
                                case 3:
                                    //WELL DONE
                                    break;
                            }
                        } else if (getCurrentSteakCategory() == SteakCategories.FILLET) {
                            mCurrentMeat = mSteakFilletMeat;
                            createAlertDialog(mCurrentMeat[position]);
                            switch (position) {
                                case 0:
                                    //RARE
                                    break;
                                case 1:
                                    //MEDIUM RARE
                                    break;
                                case 2:
                                    //MEDIUM
                                    break;
                                case 3:
                                    //WELL DONE
                                    break;
                            }
                        }
                    }
                    mGridView.setAdapter(mBaseAdapter);
                }
            }
        });
    }

    /**
     * Initialise all meat objects with times and appropriate parameters
     */
    private void initialiseMeats() {
        //bitmaps
        initialiseBitmaps();

        //categories
        initialiseCategories();

        //meats + subcategories
        initialiseChickenSubCategories();
        initialiseBeef();
        initialiseFish();
        initialiseSteak();
        initialiseLamb();
        initialisePig();
        initialiseTurkey();
    }

    private void initialiseTurkey() {
        mTurkeyMeat = new Meat[1];
        mTurkeyMeat[0] = new Meat("Roast Turkey", false, b_roast_chicken);
    }

    private void initialisePig() {
        mPigMeat = new Meat[1];
        mPigMeat[0] = new Meat("Roast Ham", false, b_beef);
    }

    private void initialiseLamb() {
        mLambMeat = new Meat[3];
        mLambMeat[0] = new Meat("Lamb Chops", Timings.LAMB_CHOPS, true, b_steak);
        mLambMeat[1] = new Meat("Lamb Loin", Timings.LAMB_LOIN, false, b_steak);
        mLambMeat[2] = new Meat("Lamb Burgers", Timings.LAMB_BURGERS, true, b_burger);
    }

    private void initialiseSteak() {
        mSteakCategory = new MeatCategory[3];
        mSteakCategory[0] = new MeatCategory("Fillet", b_steak);
        mSteakCategory[1] = new MeatCategory("Strip Loin", b_steak);
        mSteakCategory[2] = new MeatCategory("T-Bone", b_steak);

        mSteakFilletMeat = new Meat[4];
        mSteakFilletMeat[0] = new Meat("Rare", Timings.FILLET_STEAK_RARE, true, b_steak);
        mSteakFilletMeat[1] = new Meat("Medium Rare", Timings.FILLET_STEAK_MEDIUM_RARE, b_steak);
        mSteakFilletMeat[2] = new Meat("Medium", Timings.FILLET_STEAK_MEDIUM, b_steak);
        mSteakFilletMeat[3] = new Meat("Well Done", Timings.FILLET_STEAK_WELL_DONE, b_steak);

        mSteakTBoneMeat = new Meat[4];
        mSteakTBoneMeat[0] = new Meat("Rare", Timings.T_BONE_RARE, true, b_steak);
        mSteakTBoneMeat[1] = new Meat("Medium Rare", Timings.T_BONE_MEDIUM_RARE, true, b_steak);
        mSteakTBoneMeat[2] = new Meat("Medium", Timings.T_BONE_MEDIUM, true, b_steak);
        mSteakTBoneMeat[3] = new Meat("Well Done", Timings.T_BONE_WELL_DONE, true, b_steak);

        mSteakStripLoinMeat = new Meat[4];
        mSteakStripLoinMeat[0] = new Meat("Rare", Timings.STRIP_LOIN_RARE, true, b_steak);
        mSteakStripLoinMeat[1] = new Meat("Medium Rare", Timings.STRIP_LOIN_MEDIUM_RARE, true, b_steak);
        mSteakStripLoinMeat[2] = new Meat("Medium", Timings.STRIP_LOIN_MEDIUM, true, b_steak);
        mSteakStripLoinMeat[3] = new Meat("Well Done", Timings.STRIP_LOIN_WELL_DONE, true, b_steak);
    }

    private void initialiseFish() {
        mFishMeat = new Meat[1];
        mFishMeat[0] = new Meat("Fish", true, b_fish);
    }

    private void initialiseBeef() {
        mBeefMeat = new Meat[2];
        mBeefMeat[0] = new Meat("Beef Burger", Timings.BEEF_BURGERS, true, b_burger);
        mBeefMeat[1] = new Meat("Roast Beef", false, b_beef);
    }

    private void initialiseChickenSubCategories() {
        mChickenCategory = new MeatCategory[4];
        mChickenCategory[0] = new MeatCategory("BBQ", b_bbq);
        mChickenCategory[1] = new MeatCategory("Oven", b_oven);
        mChickenCategory[2] = new MeatCategory("Pan", b_pan);
        mChickenCategory[3] = new MeatCategory("Roast", b_roast_chicken);

        mChickenBBQMeat = new Meat[5];
        mChickenBBQMeat[0] = new Meat("Breast", Timings.CHICKEN_BBQ_BREAST, true, b_breast);
        mChickenBBQMeat[1] = new Meat("Strips", Timings.CHICKEN_BBQ_STRIPS, true, b_strips);
        mChickenBBQMeat[2] = new Meat("Drumsticks", Timings.CHICKEN_BBQ_DRUMSTICKS_THIGHS, true, b_drumstick);
        mChickenBBQMeat[3] = new Meat("Thighs", Timings.CHICKEN_BBQ_DRUMSTICKS_THIGHS, true, b_drumstick);
        mChickenBBQMeat[4] = new Meat("Wings", Timings.CHICKEN_BBQ_WINGS, true, b_drumstick);

        mChickenOvenMeat = new Meat[3];
        mChickenOvenMeat[0] = new Meat("Breast", Timings.CHICKEN_OVEN_BREAST, b_breast);
        mChickenOvenMeat[1] = new Meat("Thighs", Timings.CHICKEN_OVEN_WINGS_THIGHS, b_drumstick);
        mChickenOvenMeat[2] = new Meat("Wings", Timings.CHICKEN_OVEN_WINGS_THIGHS, b_drumstick);

        mChickenPanMeat = new Meat[1];
        mChickenPanMeat[0] = new Meat("Breast", Timings.CHICKEN_PAN_BREAST, b_breast);

        mChickenRoastMeat = new Meat[1];
        mChickenRoastMeat[0] = new Meat("Whole Chicken", false, b_roast_chicken);
    }

    private void initialiseCategories() {
        mCategory = new MeatCategory[7];
        mCategory[0] = new MeatCategory("Beef", b_cow);
        mCategory[1] = new MeatCategory("Chicken", b_chicken);
        mCategory[2] = new MeatCategory("Fish", b_fish);
        mCategory[3] = new MeatCategory("Lamb", b_sheep);
        mCategory[4] = new MeatCategory("Porcine", b_pig);
        mCategory[5] = new MeatCategory("Steak", b_cow);
        mCategory[6] = new MeatCategory("Turkey", b_chicken);
    }

    private void initialiseBitmaps() {
        b_chicken = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.chicken_animal, WIDTH, HEIGHT);
        b_cow = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.cow_animal, WIDTH, HEIGHT);
        b_fish = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.fish_2, WIDTH, HEIGHT);
        b_pig = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.pig_animal, WIDTH, HEIGHT);
        b_sheep = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.sheep_animal, WIDTH, HEIGHT);

        b_bbq = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.bbq, WIDTH, HEIGHT);
        b_oven = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.oven, WIDTH, HEIGHT);
        b_pan = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.pan, WIDTH, HEIGHT);
        b_roast = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.turkey_2, WIDTH, HEIGHT);

        b_strips = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.strips, WIDTH, HEIGHT);
        b_drumstick = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.drumstick, WIDTH, HEIGHT);
        b_null = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.mipmap.ic_launcher, WIDTH, HEIGHT);
        b_steak = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.steak, WIDTH, HEIGHT);
        b_beef = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.beef, WIDTH, HEIGHT);
        b_burger = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.burger, WIDTH, HEIGHT);
        b_roast_chicken = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.turkey_2, WIDTH, HEIGHT);
        b_breast = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.breast, WIDTH, HEIGHT);
    }

    void createAlertDialog(final Meat meat) {
        long estimatedEnd = System.currentTimeMillis() + meat.getDuration();
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(meat.getName())
                .setMessage(getResources().getString(R.string.meat_dialog_set,
                        Timings.formatTime(meat.getDuration()), Timings.formatTime(estimatedEnd, false)
                        + "\n" +
                        getResources().getString(R.string.ensure_heat)))
                .setPositiveButton(getString(R.string.cook_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchSetTimer(meat);
                    }
                })
                .setNegativeButton(getString(R.string.cancel_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    void launchSetTimer(Meat meat) {
        mIntent = new Intent(MainActivity.this, Timer.class);
        mIntent.putExtra("MEAT", meat.getName());
        mIntent.putExtra("TIME", meat.getDuration());
        mIntent.putExtra("FLIP", meat.isFlip());
        startActivity(mIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrentMeatCategory(MeatCategories currentMeatCategory) {
        this.mCurrentMeatCategory = currentMeatCategory;
    }

    private MeatCategories getCurrentMeatCategory() {
        return this.mCurrentMeatCategory;
    }

    private void setCurrentChickenCategory(ChickenCategories currentChickenCategory) {
        this.mCurrentChickenCategory = currentChickenCategory;
    }

    private ChickenCategories getCurrentChickenCategory() {
        return this.mCurrentChickenCategory;
    }

    private void setCurrentSteakCategory(SteakCategories currentSteakCategory) {
        this.mCurrentSteakCategory = currentSteakCategory;
    }

    private SteakCategories getCurrentSteakCategory() {
        return this.mCurrentSteakCategory;
    }

    private class Adapter extends BaseAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public Adapter(Context context) {
            this.mContext = context;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            int count = 0;

            //meats
            if (mStage == 0) {
                count = mCategory.length;
            } else if (mStage == 1) {
                //bbq, oven, pan, roast
                //also meats for other than chicken
                if (mCurrentMeatCategory == MeatCategories.CHICKEN) {
                    count = mChickenCategory.length;
                } else if (mCurrentMeatCategory == MeatCategories.BEEF) {
                    count = mBeefMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.FISH) {
                    count = mFishMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.LAMB) {
                    count = mLambMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.PIG) {
                    count = mPigMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.STEAK) {
                    count = mSteakCategory.length;
                } else if (mCurrentMeatCategory == MeatCategories.TURKEY) {
                    count = mTurkeyMeat.length;
                }
            } else if (mStage == 2) {
                //chicken meats
                if (mCurrentMeatCategory == MeatCategories.CHICKEN) {
                    if (mCurrentChickenCategory == ChickenCategories.BBQ) {
                        count = mChickenBBQMeat.length;
                    } else if (mCurrentChickenCategory == ChickenCategories.OVEN) {
                        count = mChickenOvenMeat.length;
                    } else if (mCurrentChickenCategory == ChickenCategories.PAN) {
                        count = mChickenPanMeat.length;
                    } else if (mCurrentChickenCategory == ChickenCategories.ROAST) {
                        count = mChickenRoastMeat.length;
                    }
                    //else steak meat categories
                } else if (mCurrentMeatCategory == MeatCategories.STEAK) {
                    if (mCurrentSteakCategory == SteakCategories.FILLET) {
                        count = mSteakFilletMeat.length;
                    } else if (mCurrentSteakCategory == SteakCategories.STRIPLOIN) {
                        count = mSteakStripLoinMeat.length;
                    } else if (mCurrentSteakCategory == SteakCategories.TBONE) {
                        count = mSteakTBoneMeat.length;
                    }
                }
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String title = null;
            Bitmap image = null;

            TextView textTitle;
            ImageView meatImage;

            convertView = mLayoutInflater.inflate(R.layout.tile_layout, null);
            textTitle = (TextView) convertView.findViewById(R.id.title);
            meatImage = (ImageView) convertView.findViewById(R.id.image);

            try {
                switch (mStage) {
                    case 0:
                        title = mCategory[position].getName();
                        image = mCategory[position].getImage();
                        break;
                    case 1:
                        if (mCurrentMeatCategory == MeatCategories.BEEF) {
                            title = mBeefMeat[position].getName();
                            image = mBeefMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.CHICKEN) {
                            title = mChickenCategory[position].getName();
                            image = mChickenCategory[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.FISH) {
                            title = mFishMeat[position].getName();
                            image = mFishMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.LAMB) {
                            title = mLambMeat[position].getName();
                            image = mLambMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.PIG) {
                            title = mPigMeat[position].getName();
                            image = mPigMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.STEAK) {
                            title = mSteakCategory[position].getName();
                            image = mSteakCategory[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.TURKEY) {
                            title = mTurkeyMeat[position].getName();
                            image = mTurkeyMeat[position].getImage();
                        }
                        break;
                    case 2:
                        if (mCurrentMeatCategory == MeatCategories.CHICKEN) {
                            if (mCurrentChickenCategory == ChickenCategories.BBQ) {
                                title = mChickenBBQMeat[position].getName();
                                image = mChickenBBQMeat[position].getImage();
                            } else if (mCurrentChickenCategory == ChickenCategories.PAN) {
                                title = mChickenPanMeat[position].getName();
                                image = mChickenPanMeat[position].getImage();
                            } else if (mCurrentChickenCategory == ChickenCategories.OVEN) {
                                title = mChickenOvenMeat[position].getName();
                                image = mChickenOvenMeat[position].getImage();
                            } else if (mCurrentChickenCategory == ChickenCategories.ROAST) {
                                title = mChickenRoastMeat[position].getName();
                                image = mChickenRoastMeat[position].getImage();
                            }
                        } else if (mCurrentMeatCategory == MeatCategories.STEAK) {
                            if (mCurrentSteakCategory == SteakCategories.TBONE) {
                                title = mSteakTBoneMeat[position].getName();
                                image = mSteakTBoneMeat[position].getImage();
                            } else if (mCurrentSteakCategory == SteakCategories.STRIPLOIN) {
                                title = mSteakStripLoinMeat[position].getName();
                                image = mSteakStripLoinMeat[position].getImage();
                            } else if (mCurrentSteakCategory == SteakCategories.FILLET) {
                                title = mSteakFilletMeat[position].getName();
                                image = mSteakFilletMeat[position].getImage();
                            }
                        }
                        break;
                }
            } finally {
                textTitle.setText(title);
                meatImage.setImageBitmap(Bitmap.createScaledBitmap(image, WIDTH, HEIGHT, false));
            }

            return convertView;
        }
    }

    private class MeatCategory {
        String name;
        Bitmap image;

        public MeatCategory(String name, Bitmap image) {
            this.name = name;
            this.image = image;
        }

        public String getName() {
            return this.name;
        }

        public Bitmap getImage() {
            return this.image;
        }
    }

    private class Meat {
        String name;
        long duration;
        boolean isFish, isFlip;
        Bitmap image;
        float weight;

        public Meat(String name, long duration, Bitmap image) {
            this.name = name;
            this.duration = duration;
            this.image = image;
        }

        public Meat(String name, boolean isFish, Bitmap image) {
            this.name = name;
            this.isFish = isFish;
            this.image = image;
        }

        public Meat(String name, long duration, boolean isFlip, Bitmap image) {
            this.name = name;
            this.duration = duration;
            this.isFlip = isFlip;
            this.image = image;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public boolean isFish() {
            return isFish;
        }

        public boolean isFlip() {
            return this.isFlip;
        }

        public String getName() {
            return this.name;
        }

        public Bitmap getImage() {
            return this.image;
        }

        public long getDuration() {
            return this.duration;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public float getWeight() {
            return this.weight;
        }
    }
}
