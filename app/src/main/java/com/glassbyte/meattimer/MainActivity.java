package com.glassbyte.meattimer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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
import android.widget.Toast;

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
            mLambMeat[], mPorkMeat[], mTurkeyMeat[], mHamMeat[], mCurrentMeat[];

    Bitmap b_chicken, b_cow, b_fish, b_pig, b_sheep;
    Bitmap b_bbq, b_oven, b_pan, b_roast;
    Bitmap b_strips, b_drumstick, b_null;

    private enum MeatCategories {
        BEEF, CHICKEN, FISH, HAM, LAMB, PORK, STEAK, TURKEY
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
                    System.out.println("stage 0");
                    mGridView.setAdapter(new Adapter(this));
                    break;
                default:
                    System.out.println("stage 1");
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
                    else if (mMeatCategories == MeatCategories.PORK)
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                            setCurrentMeatCategory(MeatCategories.HAM);
                            break;
                        case 4:
                            setCurrentMeatCategory(MeatCategories.LAMB);
                            break;
                        case 5:
                            setCurrentMeatCategory(MeatCategories.PORK);
                            break;
                        case 6:
                            setCurrentMeatCategory(MeatCategories.STEAK);
                            break;
                        case 7:
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
                                break;
                            case 1:
                                //ROAST BEEF
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.FISH) {
                        mCurrentMeat = mFishMeat;
                        switch (position) {
                            case 0:
                                //FISH
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.HAM) {
                        mCurrentMeat = mHamMeat;
                        switch (position) {
                            case 0:
                                //ROAST HAM
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.LAMB) {
                        mCurrentMeat = mLambMeat;
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
                    } else if (getCurrentMeatCategory() == MeatCategories.PORK) {
                        mCurrentMeat = mPorkMeat;
                        switch (position) {
                            case 0:
                                //???
                                break;
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.STEAK) {
                        switch(position) {
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
                    }
                    mGridView.setAdapter(mBaseAdapter);
                } else if (mStage == 2) {
                    //either chicken or steak
                    if (getCurrentMeatCategory() == MeatCategories.CHICKEN) {
                        if (getCurrentChickenCategory() == ChickenCategories.BBQ) {
                            mCurrentMeat = mChickenBBQMeat;
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
                            switch (position) {
                                case 0:
                                    //BREAST
                                    break;
                            }
                        } else if (getCurrentChickenCategory() == ChickenCategories.ROAST) {
                            switch (position) {
                                case 0:
                                    //WHOLE CHICKEN
                                    break;
                            }
                        }
                    } else if (getCurrentMeatCategory() == MeatCategories.STEAK) {
                        if(getCurrentSteakCategory() == SteakCategories.TBONE) {
                            mCurrentMeat = mSteakTBoneMeat;
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
                        } else if(getCurrentSteakCategory() == SteakCategories.STRIPLOIN) {
                            mCurrentMeat = mSteakStripLoinMeat;
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
                        } else if(getCurrentSteakCategory() == SteakCategories.FILLET) {
                            mCurrentMeat = mSteakFilletMeat;
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
                    Toast.makeText(MainActivity.this, Timings.formatTime(
                            mCurrentMeat[position].getDuration(), true), Toast.LENGTH_LONG).show();
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

        int height = 128, width = 128;

        b_chicken = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.chicken_animal, width, height);
        b_cow = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.cow_animal, width, height);
        b_fish = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.fish_2, width, height);
        b_pig = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.pig_animal, width, height);
        b_sheep = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.sheep_animal, width, height);

        b_bbq = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.bbq, width, height);
        b_oven = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.oven, width, height);
        b_pan = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.pan, width, height);
        b_roast = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.turkey_2, width, height);

        b_strips = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.strips, width, height);
        b_drumstick = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.drawable.drumstick, width, height);
        b_null = Helpers.decodeSampledBitmapFromResource(this.getResources(), R.mipmap.ic_launcher, width, height);

        mCategory = new MeatCategory[8];
        mCategory[0] = new MeatCategory("Beef", b_cow);
        mCategory[1] = new MeatCategory("Chicken", b_chicken);
        mCategory[2] = new MeatCategory("Fish", b_fish);
        mCategory[3] = new MeatCategory("Ham", b_pig);
        mCategory[4] = new MeatCategory("Lamb", b_sheep);
        mCategory[5] = new MeatCategory("Pork", b_pig);
        mCategory[6] = new MeatCategory("Steak", b_cow);
        mCategory[7] = new MeatCategory("Turkey", b_chicken);

        mChickenCategory = new MeatCategory[4];
        mChickenCategory[0] = new MeatCategory("BBQ", null);
        mChickenCategory[1] = new MeatCategory("Oven", null);
        mChickenCategory[2] = new MeatCategory("Pan", null);
        mChickenCategory[3] = new MeatCategory("Roast", null);

        mChickenBBQMeat = new Meat[5];
        mChickenBBQMeat[0] = new Meat("Breast", Timings.CHICKEN_BBQ_BREAST, true, b_bbq);
        mChickenBBQMeat[1] = new Meat("Strips", Timings.CHICKEN_BBQ_STRIPS, true, b_strips);
        mChickenBBQMeat[2] = new Meat("Drumsticks", Timings.CHICKEN_BBQ_DRUMSTICKS_THIGHS, true, b_drumstick);
        mChickenBBQMeat[3] = new Meat("Thighs", Timings.CHICKEN_BBQ_DRUMSTICKS_THIGHS, true, b_drumstick);
        mChickenBBQMeat[4] = new Meat("Wings", Timings.CHICKEN_BBQ_WINGS, true, b_drumstick);

        mChickenOvenMeat = new Meat[3];
        mChickenOvenMeat[0] = new Meat("Breast", Timings.CHICKEN_OVEN_BREAST, b_null);
        mChickenOvenMeat[1] = new Meat("Thighs", Timings.CHICKEN_OVEN_WINGS_THIGHS, b_null);
        mChickenOvenMeat[2] = new Meat("Wings", Timings.CHICKEN_OVEN_WINGS_THIGHS, b_null);

        mChickenPanMeat = new Meat[1];
        mChickenPanMeat[0] = new Meat("Breast", Timings.CHICKEN_PAN_BREAST, b_null);

        mChickenRoastMeat = new Meat[1];
        mChickenRoastMeat[0] = new Meat("Whole Chicken", false, b_null);

        mBeefMeat = new Meat[2];
        mBeefMeat[0] = new Meat("Beef Burger", Timings.BEEF_BURGERS, true, b_null);
        mBeefMeat[1] = new Meat("Roast", false, b_null);

        mFishMeat = new Meat[1];
        mFishMeat[0] = new Meat("Fish", true, b_null);

        mSteakCategory = new MeatCategory[3];
        mSteakCategory[0] = new MeatCategory("Fillet", null);
        mSteakCategory[1] = new MeatCategory("Strip Loin", null);
        mSteakCategory[2] = new MeatCategory("T-Bone", null);

        mSteakFilletMeat = new Meat[4];
        mSteakFilletMeat[0] = new Meat("Rare", Timings.FILLET_STEAK_RARE, true, b_null);
        mSteakFilletMeat[1] = new Meat("Medium Rare", Timings.FILLET_STEAK_MEDIUM_RARE, b_null);
        mSteakFilletMeat[2] = new Meat("Medium", Timings.FILLET_STEAK_MEDIUM, b_null);
        mSteakFilletMeat[3] = new Meat("Well Done", Timings.FILLET_STEAK_WELL_DONE, b_null);

        mSteakTBoneMeat = new Meat[4];
        mSteakTBoneMeat[0] = new Meat("Rare", Timings.T_BONE_RARE, true, b_null);
        mSteakTBoneMeat[1] = new Meat("Medium Rare", Timings.T_BONE_MEDIUM_RARE, true, b_null);
        mSteakTBoneMeat[2] = new Meat("Medium", Timings.T_BONE_MEDIUM, true, b_null);
        mSteakTBoneMeat[3] = new Meat("Well Done", Timings.T_BONE_WELL_DONE, true, b_null);

        mSteakStripLoinMeat = new Meat[4];
        mSteakStripLoinMeat[0] = new Meat("Rare", Timings.STRIP_LOIN_RARE, true, b_null);
        mSteakStripLoinMeat[1] = new Meat("Medium Rare", Timings.STRIP_LOIN_MEDIUM_RARE, true, b_null);
        mSteakStripLoinMeat[2] = new Meat("Medium", Timings.STRIP_LOIN_MEDIUM, true, b_null);
        mSteakStripLoinMeat[3] = new Meat("Well Done", Timings.STRIP_LOIN_WELL_DONE, true, b_null);

        mLambMeat = new Meat[3];
        mLambMeat[0] = new Meat("Lamb Chops", Timings.LAMB_CHOPS, true, b_null);
        mLambMeat[1] = new Meat("Lamb Loin", Timings.LAMB_LOIN, false, b_null);
        mLambMeat[2] = new Meat("Lamb Burgers", Timings.LAMB_BURGERS, true, b_null);

        mHamMeat = new Meat[1];
        mHamMeat[0] = new Meat("Roast Ham", false, b_null);

        mPorkMeat = new Meat[1];
        mPorkMeat[0] = new Meat("hmm...", 0, true, b_null);

        mTurkeyMeat = new Meat[1];
        mTurkeyMeat[0] = new Meat("Roast Turkey", false, b_null);
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
                } else if (mCurrentMeatCategory == MeatCategories.HAM) {
                    count = mHamMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.LAMB) {
                    count = mLambMeat.length;
                } else if (mCurrentMeatCategory == MeatCategories.PORK) {
                    count = mPorkMeat.length;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String title = null;
            Bitmap image = null;

            View view = convertView;
            TextView textTitle;
            ImageView meatImage;

            if (view == null) {
                view = mLayoutInflater.inflate(R.layout.tile_layout, null);
                textTitle = (TextView) view.findViewById(R.id.title);
                meatImage = (ImageView) view.findViewById(R.id.image);

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
                        } else if (mCurrentMeatCategory == MeatCategories.HAM) {
                            title = mHamMeat[position].getName();
                            image = mHamMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.LAMB) {
                            title = mLambMeat[position].getName();
                            image = mLambMeat[position].getImage();
                        } else if (mCurrentMeatCategory == MeatCategories.PORK) {
                            title = mPorkMeat[position].getName();
                            image = mPorkMeat[position].getImage();
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
                textTitle.setText(title);
                meatImage.setImageBitmap(Bitmap.createScaledBitmap(image, 120, 120, false));
            }
            return view;
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
        public Bitmap getImage(){return this.image;}
    }

    private class Meat {
        String name;
        long duration;
        boolean isFish, isFlip;
        Bitmap image;

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
    }
}
