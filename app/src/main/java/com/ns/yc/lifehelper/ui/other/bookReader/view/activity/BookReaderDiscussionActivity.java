package com.ns.yc.lifehelper.ui.other.bookReader.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ns.yc.lifehelper.R;
import com.ns.yc.lifehelper.comment.ConstantBookReader;
import com.ns.yc.lifehelper.base.mvp1.BaseActivity;
import com.ns.yc.lifehelper.model.event.BookReaderSelectionEvent;
import com.ns.yc.lifehelper.ui.other.bookReader.view.fragment.BookReaderDiscussionFragment;
import com.ns.yc.lifehelper.weight.SelectionLayout;
import com.ns.yc.lifehelper.utils.EventBusUtils;

import java.util.List;

import butterknife.Bind;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/9/27
 * 描    述：综合讨论区
 * 修订历史：
 * ================================================
 */
public class BookReaderDiscussionActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.sl_layout)
    SelectionLayout slLayout;
    private String readType;
    private String name;


    @Override
    public int getContentView() {
        return R.layout.base_selection_view;
    }

    @Override
    public void initView() {
        initIntent();
        initToolBar();
        initAddFragment();
    }

    private void initIntent() {
        if(getIntent()!=null){
            readType = getIntent().getStringExtra("type");
            name = getIntent().getStringExtra("name");
        }
        if(TextUtils.isEmpty(readType)){
            readType = "ramble";
        }
        if(TextUtils.isEmpty(name)){
            name = "综合讨论区";
        }
    }

    private void initToolBar() {
        toolbarTitle.setText(name);
    }


    @Override
    public void initListener() {
        llTitleMenu.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                finish();
                break;
        }
    }

    private void initAddFragment() {
        BookReaderDiscussionFragment fragment = BookReaderDiscussionFragment.newInstance(readType);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }


    @ConstantBookReader.Distillate
    private String distillate = ConstantBookReader.Distillate.ALL;
    @ConstantBookReader.BookType
    private String type = ConstantBookReader.BookType.ALL;
    @ConstantBookReader.SortType
    private String sort = ConstantBookReader.SortType.DEFAULT;
    private void getData() {
        final List<List<String>> lists = ConstantBookReader.list1;
        if (slLayout != null) {
            List[] num = new List[lists.size()];
            slLayout.setData(lists.toArray(num));
            slLayout.setOnSelectListener(new SelectionLayout.OnSelectListener() {
                @Override
                public void onSelect(int index, int position, String title) {
                    switch (index) {
                        case 0:
                            switch (position) {
                                case 0:
                                    distillate = ConstantBookReader.Distillate.ALL;
                                    break;
                                case 1:
                                    distillate = ConstantBookReader.Distillate.DISTILLATE;
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 1:
                            if (lists.size() == 2) {
                                sort = ConstantBookReader.sortTypeList.get(position);
                            } else if (lists.size() == 3) {
                                type = ConstantBookReader.bookTypeList.get(position);
                            }
                            break;
                        case 2:
                            sort = ConstantBookReader.sortTypeList.get(position);
                            break;
                        default:
                            break;
                    }
                    BookReaderSelectionEvent bookReaderSelectionEvent = new BookReaderSelectionEvent(distillate, type, sort);
                    EventBusUtils.post(bookReaderSelectionEvent);
                }
            });
        }
    }

}
