//package Adapters;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import Fragments.FirstFragment;
//import Fragments.FragmentPhotos;
//import Fragments.SecondFragment;
//import Fragments.ThirdFragment;
//
//public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
//    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragment) {
//        super(fragment);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new FirstFragment();
//            case 1:
//                return new SecondFragment();
//            case 2:
//                return new ThirdFragment();
//            default:
//                return new FragmentPhotos();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 3;
//    }
//}
