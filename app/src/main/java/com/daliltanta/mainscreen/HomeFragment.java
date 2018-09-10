package com.daliltanta.mainscreen;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.daliltanta.R;
import com.daliltanta.UI.FontTextView;
import com.daliltanta.UI.NavigationIconClickListener;
import com.daliltanta.UI.RecyclerItemDecoration;
import com.daliltanta.UI.StaggeredSubItemCardRecyclerViewAdapter;
import com.daliltanta.adapters.All_ItemsAdapter;
import com.daliltanta.data.Category;
import com.daliltanta.data.SubItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedelshaer on 10/16/17.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout sliderShow;
    private DatabaseReference databaseReference;
    ArrayList<String> mainItemArrayList = new ArrayList<>();
    static ArrayList<Category> categoryArrayList = new ArrayList<>();
    FirebaseUser firebaseUser;
    All_ItemsAdapter all_itemsAdapter;
    ViewPager viewPagerMain;
    //    ViewPager viewpagerCategory;
    TabLayout tabLayoutMainItem;
    ArrayList<SubItem> subItems = new ArrayList<>();

    //    TabLayout tabLayoutCategory;
    LottieAnimationView lottieLoading, LottieLoadingNested;
    LinearLayout ll;
    public RecyclerView rv;
    Toolbar toolbar;
    RecyclerView backdrop_rv;
    CategoriesAdapter categoriesAdapter;
    GridLayoutManager gridLayoutManager;
    NestedScrollView product_grid;

    public HomeFragment() {
        // Requires empty public constructor
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        toolbar = root.findViewById(R.id.app_bar);
//        sliderShow = (SliderLayout) root.findViewById(R.id.slider);
//        sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);
//        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderShow.setCustomAnimation(new DescriptionAnimation());
//        sliderShow.setDuration(4000);
//        viewPagerMain = (ViewPager) root.findViewById(R.id.viewpagerMainItem);
//        viewpagerCategory = (ViewPager) root.findViewById(R.id.viewpagerCategory);
//        tabLayoutCategory = (TabLayout) root.findViewById(R.id.sliding_tabsCategory);
//        tabLayoutMainItem = (TabLayout) root.findViewById(R.id.sliding_MainItemtabs);
        backdrop_rv = root.findViewById(R.id.backdropRV);
        lottieLoading = root.findViewById(R.id.lottieLoading);
        ll = root.findViewById(R.id.myLinearLayout);
        product_grid = root.findViewById(R.id.product_grid);
//        LottieLoadingNested = root.findViewById(R.id.lottieLoadingNested);
//        LottieLoadingNested.setVisibility(View.VISIBLE);
//        lottieLoading.setVisibility(View.VISIBLE);
//        ll.setVisibility(View.GONE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // Set up the toolbar
        setUpToolbar(root);
        getCategoriesFromFirebase();
//        getSliderItems();
        rv = root.findViewById(R.id.rvItems);
        getMainItemFromFirebaseToMyITems("clothes");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            root.findViewById(R.id.product_grid).setBackground(getContext().getDrawable(R.drawable.product_grid_background_shape));
        }


        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 2 ? 2 : 1;
            }
        });
        int largePadding = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.small_margin);
        rv.addItemDecoration(new RecyclerItemDecoration(largePadding, smallPadding));


        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.product_grid),
                new AccelerateDecelerateInterpolator(),
                getContext().getResources().getDrawable(R.drawable.arrow), // Menu open icon
                getContext().getResources().getDrawable(R.drawable.ic_close_black_24dp))); // Menu close icon

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.categories_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    //get categories from database
    public void getCategoriesFromFirebase() {
        DatabaseReference categRef = databaseReference.child("categories");
        categRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mainItemArrayList.clear();
                categoryArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);

                    categoryArrayList.add(category);
                }

                categoriesAdapter = new CategoriesAdapter(getActivity(), categoryArrayList);
                backdrop_rv.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                backdrop_rv.setLayoutManager(gridLayoutManager);
                backdrop_rv.setAdapter(categoriesAdapter);

//                viewpagerCategory.setAdapter(new HomeFragmentPagerAdapter(getChildFragmentManager(),
//                        getActivity(), categoryArrayList));
//                tabLayoutCategory.setupWithViewPager(viewpagerCategory);
//


                lottieLoading.pauseAnimation();
//                LottieLoadingNested.setVisibility(View.GONE);
                lottieLoading.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //end of retrieving


    //    slider starts
    private void getSliderItems() {
        databaseReference.child("slider_items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = "";
                List<String> imageList = new ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    image = (String) postSnapshot.child("image").getValue();
                    imageList.add(image);
                    // initialize a SliderLayout

                }

                if (!imageList.equals(null)) {
                    for (int i = 0; i < imageList.size(); i++) {
                        DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());

                        defaultSliderView
                                .image(imageList.get(i))
                                .setScaleType(BaseSliderView.ScaleType.Fit);
//                        sliderShow.addSlider(defaultSliderView);


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop() {
//        sliderShow.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
//        sliderShow.startAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //slider ends


    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
        private final Context context;
        private ArrayList<Category> categorie_arraylist = new ArrayList<>();
        private DatabaseReference databaseReference;
        LottieAnimationView LottieLoadingNested;
        ArrayList<String> titles = new ArrayList<>();
        FirebaseUser firebaseUser;

        public CategoriesAdapter(Context context, ArrayList<Category> categoryArrayList) {
            this.context = context;
            this.categorie_arraylist = categoryArrayList;
        }

        @Override
        public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        databaseReference = FirebaseDatabase.getInstance().getReference();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_details, parent, false);

            return new CategoriesAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, int position) {
            final Category category = categorie_arraylist.get(position);
            holder.fontTextViewCategory.setText(category.getId());
            Glide.with(context).load(category.getCategory_image()).into(holder.imageViewCategory);
            titles.add(category.getId());
        }


        @Override
        public int getItemCount() {
            return categorie_arraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            FontTextView fontTextViewCategory;
            ImageView imageViewCategory;

            public ViewHolder(View itemView) {
                super(itemView);
                fontTextViewCategory = itemView.findViewById(R.id.categry);
                imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View view) {
                String title = titles.get(getAdapterPosition());
                getMainItemFromFirebaseToMyITems(title);
                toolbar.setTitle(title);

            }
        }


    }
    public void getMainItemFromFirebaseToMyITems(final String title) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference categRef = databaseReference.child("SubItem");

        categRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                subItems.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SubItem subItem= postSnapshot.getValue(SubItem.class);
                    if(subItem.getSubItem_id().equals(title)){
                        subItems.add(subItem);
                    }

                }
                rv.setHasFixedSize(true);

                rv.setLayoutManager(gridLayoutManager);

                StaggeredSubItemCardRecyclerViewAdapter adapter = new StaggeredSubItemCardRecyclerViewAdapter(subItems,getContext());
                rv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}