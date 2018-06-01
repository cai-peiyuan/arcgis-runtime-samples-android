/* Copyright 2018 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgisruntime.sample.statisticalquery;

import java.util.LinkedHashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Gets results from the main activity through an Intent extra, converts the results to a LinkedHashMap, and creates an
 * ExpandableListView to display the results.
 */
public class ResultsActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.results_expandablelistview);

    // get intent from main activity
    Intent intent = getIntent();
    Gson gson = new Gson();
    // get linked hash map from intent
    LinkedHashMap<String, List<String>> groupedStatistics = gson
        .fromJson(intent.getStringExtra("results"), new ResultsActivity.LinkedHashMapTypeToken().getType());

    // create expandable list view
    ExpandableListView expandableListView = findViewById(R.id.expandableListView);
    ExpandableListAdapter expandableListAdapter = new ExpandableListViewAdapter(this, groupedStatistics);
    expandableListView.setAdapter(expandableListAdapter);
    // expand the first group by default
    expandableListView.expandGroup(0);
  }

  private static class LinkedHashMapTypeToken extends TypeToken<LinkedHashMap<String, List<String>>> {
  }
}
