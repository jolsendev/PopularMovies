package com.example.jamie.popularmovies;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;

import com.example.jamie.popularmovies.data.MovieContract;
import com.example.jamie.popularmovies.data.MovieProvider;

/**
 * Created by a5w5nzz on 11/21/2016.
 */

public class TestProvider extends AndroidTestCase {
    public void testProviderRegistry() {

        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            System.out.println("Authority is at: "+ providerInfo.authority);
            System.out.println("My authority is at: "+MovieContract.CONTENT_AUTHORITY);
            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
}
