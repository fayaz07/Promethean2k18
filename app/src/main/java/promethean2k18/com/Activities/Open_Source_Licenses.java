package promethean2k18.com.Activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import promethean2k18.com.R;

public class Open_Source_Licenses extends AppCompatActivity {

    TextView openS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source_licences);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.about_facebook_color)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Promethean 2k18");

        openS = findViewById(R.id.opensorucetext);

        String open = "        <h1>Privacy Policy</h1>\n" +
                "\n" +
                "\n" +
                "<p>Effective date: November 25, 2018</p>\n" +
                "\n" +
                "\n" +
                "<p>B V Raju Institute of Technology, Narsapur, Telangana operates the promethean2k18.com website and the Promethean 2k18 mobile application (the \"Service\").</p>\n" +
                "\n" +
                "<p>This page informs you of our policies regarding the collection, use, and disclosure of personal data when you use our Service and the choices you have associated with that data. </p>\n" +
                "\n" +
                "<p>We use your data to provide and improve the Service. By using the Service, you agree to the collection and use of information in accordance with this policy. Unless otherwise defined in this Privacy Policy, terms used in this Privacy Policy have the same meanings as in our Terms and Conditions.</p>\n" +
                "\n" +
                "\n" +
                "<h2>Information Collection And Use</h2>\n" +
                "\n" +
                "<p>We collect several different types of information for various purposes to provide and improve our Service to you.</p>\n" +
                "\n" +
                "<h3>Types of Data Collected</h3>\n" +
                "\n" +
                "<h4>Personal Data</h4>\n" +
                "\n" +
                "<p>While using our Service, we may ask you to provide us with certain personally identifiable information that can be used to contact or identify you (\"Personal Data\"). Personally identifiable information may include, but is not limited to:</p>\n" +
                "\n" +
                "<ul>\n" +
                "<li>Email address</li><li>First name and last name</li><li>Phone number</li><li>Address, State, Province, ZIP/Postal code, City</li><li>Cookies and Usage Data</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h4>Usage Data</h4>\n" +
                "\n" +
                "<p>We may also collect information that your browser sends whenever you visit our Service or when you access the Service by or through a mobile device (\"Usage Data\").</p>\n" +
                "<p>This Usage Data may include information such as your computers Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that you visit, the time and date of your visit, the time spent on those pages, unique device identifiers and other diagnostic data.</p>\n" +
                "<p>When you access the Service by or through a mobile device, this Usage Data may include information such as the type of mobile device you use, your mobile device unique ID, the IP address of your mobile device, your mobile operating system, the type of mobile Internet browser you use, unique device identifiers and other diagnostic data.</p>\n" +
                "\n" +
                "<h4>Tracking and Cookies Data</h4>\n" +
                "<p>We use cookies and similar tracking technologies to track the activity on our Service and hold certain information.</p>\n" +
                "<p>Cookies are files with small amount of data which may include an anonymous unique identifier. Cookies are sent to your browser from a website and stored on your device. Tracking technologies also used are beacons, tags, and scripts to collect and track information and to improve and analyze our Service.</p>\n" +
                "<p>You can instruct your browser to refuse all cookies or to indicate when a cookie is being sent. However, if you do not accept cookies, you may not be able to use some portions of our Service.</p>\n" +
                "<p>Examples of Cookies we use:</p>\n" +
                "<ul>\n" +
                "    <li><strong>Session Cookies.</strong> We use Session Cookies to operate our Service.</li>\n" +
                "    <li><strong>Preference Cookies.</strong> We use Preference Cookies to remember your preferences and various settings.</li>\n" +
                "    <li><strong>Security Cookies.</strong> We use Security Cookies for security purposes.</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Use of Data</h2>\n" +
                "\n" +
                "<p>Educational Institution uses the collected data for various purposes:</p>\n" +
                "<ul>\n" +
                "    <li>To provide and maintain the Service</li>\n" +
                "    <li>To notify you about changes to our Service</li>\n" +
                "    <li>To allow you to participate in interactive features of our Service when you choose to do so</li>\n" +
                "    <li>To provide customer care and support</li>\n" +
                "    <li>To provide analysis or valuable information so that we can improve the Service</li>\n" +
                "    <li>To monitor the usage of the Service</li>\n" +
                "    <li>To detect, prevent and address technical issues</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Transfer Of Data</h2>\n" +
                "<p>Your information, including Personal Data, may be transferred to — and maintained on — computers located outside of your state, province, country or other governmental jurisdiction where the data protection laws may differ than those from your jurisdiction.</p>\n" +
                "<p>If you are located outside India and choose to provide information to us, please note that we transfer the data, including Personal Data, to India and process it there.</p>\n" +
                "<p>Your consent to this Privacy Policy followed by your submission of such information represents your agreement to that transfer.</p>\n" +
                "<p>Educational Institution will take all steps reasonably necessary to ensure that your data is treated securely and in accordance with this Privacy Policy and no transfer of your Personal Data will take place to an organization or a country unless there are adequate controls in place including the security of your data and other personal information.</p>\n" +
                "\n" +
                "<h2>Disclosure Of Data</h2>\n" +
                "\n" +
                "<h3>Legal Requirements</h3>\n" +
                "<p>Educational Institution may disclose your Personal Data in the good faith belief that such action is necessary to:</p>\n" +
                "<ul>\n" +
                "    <li>To comply with a legal obligation</li>\n" +
                "    <li>To protect and defend the rights or property of Educational Institution</li>\n" +
                "    <li>To prevent or investigate possible wrongdoing in connection with the Service</li>\n" +
                "    <li>To protect the personal safety of users of the Service or the public</li>\n" +
                "    <li>To protect against legal liability</li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>Security Of Data</h2>\n" +
                "<p>The security of your data is important to us, but remember that no method of transmission over the Internet, or method of electronic storage is 100% secure. While we strive to use commercially acceptable means to protect your Personal Data, we cannot guarantee its absolute security.</p>\n" +
                "\n" +
                "<h2>Service Providers</h2>\n" +
                "<p>We may employ third party companies and individuals to facilitate our Service (\"Service Providers\"), to provide the Service on our behalf, to perform Service-related services or to assist us in analyzing how our Service is used.</p>\n" +
                "<p>These third parties have access to your Personal Data only to perform these tasks on our behalf and are obligated not to disclose or use it for any other purpose.</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h2>Links To Other Sites</h2>\n" +
                "<p>Our Service may contain links to other sites that are not operated by us. If you click on a third party link, you will be directed to that third partys site. We strongly advise you to review the Privacy Policy of every site you visit.</p>\n" +
                "<p>We have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.</p>\n" +
                "\n" +
                "\n" +
                "<h2>Childrens Privacy</h2>\n" +
                "<p>Our Service does not address anyone under the age of 18 (\"Children\").</p>\n" +
                "<p>We do not knowingly collect personally identifiable information from anyone under the age of 18. If you are a parent or guardian and you are aware that your Children has provided us with Personal Data, please contact us. If we become aware that we have collected Personal Data from children without verification of parental consent, we take steps to remove that information from our servers.</p>\n" +
                "\n" +
                "\n" +
                "<h2>Changes To This Privacy Policy</h2>\n" +
                "<p>We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page.</p>\n" +
                "<p>We will let you know via email and/or a prominent notice on our Service, prior to the change becoming effective and update the \"effective date\" at the top of this Privacy Policy.</p>\n" +
                "<p>You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.</p>\n" +
                "\n" +
                "\n" +
                "<h2>Contact Us</h2>\n" +
                "<p>If you have any questions about this Privacy Policy, please contact us:</p>\n" +
                "<ul>\n" +
                "        <li>By email: admin@promethean2k18.com</li>\n" +
                "\n" +
                "        </ul>\n" +
                "\n" +
                "<h5> I thank the open source contributors for their libraries, Google, StackOverFlow and all other icons that I have used in my app </h5>\n" +
                "\n";

        openS.setText(Html.fromHtml(open));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
