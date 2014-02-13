package course.labs.fragmentslab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment 
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
			
			mFriendsFragment = new FriendsFragment();

			//TODO 1 - add the FriendsFragment to the fragment_container
			fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.fragment_container, mFriendsFragment);
			fragmentTransaction.commit();
			

		} else {

			// Otherwise, save a reference to the FeedFragment for later use

			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
		if( mFriendsFragment != null && mFriendsFragment instanceof FriendsFragment ){
			fragmentManager.putFragment(outState, "mFriend",  mFriendsFragment);
		}
		if( mFeedFragment != null && mFeedFragment instanceof FeedFragment ){
			fragmentManager.putFragment(outState, "mFeed",   mFeedFragment);
		}
		
	}
	
	private void restoreFragments(Bundle inState){
		
		if( inState != null ){
			if(  fragmentManager.findFragmentByTag("mFriend") != null ){
				mFriendsFragment = (FriendsFragment)fragmentManager.findFragmentByTag("mFriend");
			}
			if(  fragmentManager.findFragmentByTag("mFeed") != null ){
				mFeedFragment = (FeedFragment)fragmentManager.findFragmentByTag("mFeed");
			}
		}
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle inState) {
		Log.i(TAG, "onRestoreInstanceState");
		restoreFragments(inState);
		}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {

			//TODO 2 - replace the fragment_container with the FeedFragment
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container, mFeedFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			

			// execute transaction now
			getFragmentManager().executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
