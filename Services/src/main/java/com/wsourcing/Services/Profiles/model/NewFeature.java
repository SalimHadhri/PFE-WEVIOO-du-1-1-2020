package com.wsourcing.Services.Profiles.model;


//All features detained by the candidate
public class NewFeature {

    private int mean_experiences ;
    private int total_experience;
    private int nb_skills;
    private int nb_total_endorsments;
    private int nb_total_experience;
    private int nb_total_education;
    private int nb_projects;
    private int nb_certifs;
    private int categ_seniority;
    private int employed;
    private int inTunisia;
    private int  new_job;
    private int lasting_in_current_company;
    private int possible_departure;
    private SkillsDuration skills_duration ;
    private TechProfile tech_profile_1;
    private TechProfile  tech_profile_2;
    private TechProfile tech_profile_3;
    private TechProfile  tech_profile_4;
    private TechProfile tech_profile_5;
    private TechProfile tech_profile_6;
    private TechProfile tech_profile_7;
    private TechProfile tech_profile_8;
    private TechProfile tech_profile_9;

    private String best_profile ;
    private float best_profile_techno_score ;
    private float  best_profile_mean_skills_duration;
    private int  best_profile_categ_seniority ;
    private float score_web_back_end ;
    private float score_front_end ;
    private float score_embarqué_middleware ;
    private float score_technical_lead__architecte_jee;
    private float  score_fullstack_js;
    private float score_java_jee;
    private float score_php_symfony;
    private float score_drupal;
    private float  score_product_owner;
    private String final_best_profile ;
    private float final_best_profile_score;
     private float final_best_profile_mean_skills_duration;
    private int final_best_profile_categ_seniority ;


    public NewFeature() {
    }

    public NewFeature(int mean_experiences, int total_experience, int nb_skills, int nb_total_endorsments, int nb_total_experience, int nb_total_education, int nb_projects, int nb_certifs, int categ_seniority, int employed, int inTunisia, int new_job, int lasting_in_current_company, int possible_departure, SkillsDuration skills_duration, TechProfile tech_profile_1, TechProfile tech_profile_2, TechProfile tech_profile_3, TechProfile tech_profile_4, TechProfile tech_profile_5, TechProfile tech_profile_6, TechProfile tech_profile_7, TechProfile tech_profile_8, TechProfile tech_profile_9, String best_profile, float best_profile_techno_score, float best_profile_mean_skills_duration, int best_profile_categ_seniority, float score_web_back_end, float score_front_end, float score_embarqué_middleware, float score_technical_lead__architecte_jee, float score_fullstack_js, float score_java_jee, float score_php_symfony, float score_drupal, float score_product_owner, String final_best_profile, float final_best_profile_score, float final_best_profile_mean_skills_duration, int final_best_profile_categ_seniority) {
        this.mean_experiences = mean_experiences;
        this.total_experience = total_experience;
        this.nb_skills = nb_skills;
        this.nb_total_endorsments = nb_total_endorsments;
        this.nb_total_experience = nb_total_experience;
        this.nb_total_education = nb_total_education;
        this.nb_projects = nb_projects;
        this.nb_certifs = nb_certifs;
        this.categ_seniority = categ_seniority;
        this.employed = employed;
        this.inTunisia = inTunisia;
        this.new_job = new_job;
        this.lasting_in_current_company = lasting_in_current_company;
        this.possible_departure = possible_departure;
        this.skills_duration = skills_duration;
        this.tech_profile_1 = tech_profile_1;
        this.tech_profile_2 = tech_profile_2;
        this.tech_profile_3 = tech_profile_3;
        this.tech_profile_4 = tech_profile_4;
        this.tech_profile_5 = tech_profile_5;
        this.tech_profile_6 = tech_profile_6;
        this.tech_profile_7 = tech_profile_7;
        this.tech_profile_8 = tech_profile_8;
        this.tech_profile_9 = tech_profile_9;
        this.best_profile = best_profile;
        this.best_profile_techno_score = best_profile_techno_score;
        this.best_profile_mean_skills_duration = best_profile_mean_skills_duration;
        this.best_profile_categ_seniority = best_profile_categ_seniority;
        this.score_web_back_end = score_web_back_end;
        this.score_front_end = score_front_end;
        this.score_embarqué_middleware = score_embarqué_middleware;
        this.score_technical_lead__architecte_jee = score_technical_lead__architecte_jee;
        this.score_fullstack_js = score_fullstack_js;
        this.score_java_jee = score_java_jee;
        this.score_php_symfony = score_php_symfony;
        this.score_drupal = score_drupal;
        this.score_product_owner = score_product_owner;
        this.final_best_profile = final_best_profile;
        this.final_best_profile_score = final_best_profile_score;
        this.final_best_profile_mean_skills_duration = final_best_profile_mean_skills_duration;
        this.final_best_profile_categ_seniority = final_best_profile_categ_seniority;
    }

    public int getMean_experiences() {
        return mean_experiences;
    }

    public void setMean_experiences(int mean_experiences) {
        this.mean_experiences = mean_experiences;
    }

    public int getTotal_experience() {
        return total_experience;
    }

    public void setTotal_experience(int total_experience) {
        this.total_experience = total_experience;
    }

    public int getNb_skills() {
        return nb_skills;
    }

    public void setNb_skills(int nb_skills) {
        this.nb_skills = nb_skills;
    }

    public int getNb_total_endorsments() {
        return nb_total_endorsments;
    }

    public void setNb_total_endorsments(int nb_total_endorsments) {
        this.nb_total_endorsments = nb_total_endorsments;
    }

    public int getNb_total_experience() {
        return nb_total_experience;
    }

    public void setNb_total_experience(int nb_total_experience) {
        this.nb_total_experience = nb_total_experience;
    }

    public int getNb_total_education() {
        return nb_total_education;
    }

    public void setNb_total_education(int nb_total_education) {
        this.nb_total_education = nb_total_education;
    }

    public int getNb_projects() {
        return nb_projects;
    }

    public void setNb_projects(int nb_projects) {
        this.nb_projects = nb_projects;
    }

    public int getNb_certifs() {
        return nb_certifs;
    }

    public void setNb_certifs(int nb_certifs) {
        this.nb_certifs = nb_certifs;
    }

    public int getCateg_seniority() {
        return categ_seniority;
    }

    public void setCateg_seniority(int categ_seniority) {
        this.categ_seniority = categ_seniority;
    }

    public int getEmployed() {
        return employed;
    }

    public void setEmployed(int employed) {
        this.employed = employed;
    }

    public int getInTunisia() {
        return inTunisia;
    }

    public void setInTunisia(int inTunisia) {
        this.inTunisia = inTunisia;
    }

    public int getNew_job() {
        return new_job;
    }

    public void setNew_job(int new_job) {
        this.new_job = new_job;
    }

    public int getLasting_in_current_company() {
        return lasting_in_current_company;
    }

    public void setLasting_in_current_company(int lasting_in_current_company) {
        this.lasting_in_current_company = lasting_in_current_company;
    }

    public int getPossible_departure() {
        return possible_departure;
    }

    public void setPossible_departure(int possible_departure) {
        this.possible_departure = possible_departure;
    }

    public SkillsDuration getSkills_duration() {
        return skills_duration;
    }

    public void setSkills_duration(SkillsDuration skills_duration) {
        this.skills_duration = skills_duration;
    }

    public TechProfile getTech_profile_1() {
        return tech_profile_1;
    }

    public void setTech_profile_1(TechProfile tech_profile_1) {
        this.tech_profile_1 = tech_profile_1;
    }

    public TechProfile getTech_profile_2() {
        return tech_profile_2;
    }

    public void setTech_profile_2(TechProfile tech_profile_2) {
        this.tech_profile_2 = tech_profile_2;
    }

    public TechProfile getTech_profile_3() {
        return tech_profile_3;
    }

    public void setTech_profile_3(TechProfile tech_profile_3) {
        this.tech_profile_3 = tech_profile_3;
    }

    public TechProfile getTech_profile_4() {
        return tech_profile_4;
    }

    public void setTech_profile_4(TechProfile tech_profile_4) {
        this.tech_profile_4 = tech_profile_4;
    }

    public TechProfile getTech_profile_5() {
        return tech_profile_5;
    }

    public void setTech_profile_5(TechProfile tech_profile_5) {
        this.tech_profile_5 = tech_profile_5;
    }

    public TechProfile getTech_profile_6() {
        return tech_profile_6;
    }

    public void setTech_profile_6(TechProfile tech_profile_6) {
        this.tech_profile_6 = tech_profile_6;
    }

    public TechProfile getTech_profile_7() {
        return tech_profile_7;
    }

    public void setTech_profile_7(TechProfile tech_profile_7) {
        this.tech_profile_7 = tech_profile_7;
    }

    public TechProfile getTech_profile_8() {
        return tech_profile_8;
    }

    public void setTech_profile_8(TechProfile tech_profile_8) {
        this.tech_profile_8 = tech_profile_8;
    }

    public TechProfile getTech_profile_9() {
        return tech_profile_9;
    }

    public void setTech_profile_9(TechProfile tech_profile_9) {
        this.tech_profile_9 = tech_profile_9;
    }

    public String getBest_profile() {
        return best_profile;
    }

    public void setBest_profile(String best_profile) {
        this.best_profile = best_profile;
    }

    public float getBest_profile_techno_score() {
        return best_profile_techno_score;
    }

    public void setBest_profile_techno_score(float best_profile_techno_score) {
        this.best_profile_techno_score = best_profile_techno_score;
    }

    public float getBest_profile_mean_skills_duration() {
        return best_profile_mean_skills_duration;
    }

    public void setBest_profile_mean_skills_duration(float best_profile_mean_skills_duration) {
        this.best_profile_mean_skills_duration = best_profile_mean_skills_duration;
    }

    public int getBest_profile_categ_seniority() {
        return best_profile_categ_seniority;
    }

    public void setBest_profile_categ_seniority(int best_profile_categ_seniority) {
        this.best_profile_categ_seniority = best_profile_categ_seniority;
    }

    public float getScore_web_back_end() {
        return score_web_back_end;
    }

    public void setScore_web_back_end(float score_web_back_end) {
        this.score_web_back_end = score_web_back_end;
    }

    public float getScore_front_end() {
        return score_front_end;
    }

    public void setScore_front_end(float score_front_end) {
        this.score_front_end = score_front_end;
    }

    public float getScore_embarqué_middleware() {
        return score_embarqué_middleware;
    }

    public void setScore_embarqué_middleware(float score_embarqué_middleware) {
        this.score_embarqué_middleware = score_embarqué_middleware;
    }

    public float getScore_technical_lead__architecte_jee() {
        return score_technical_lead__architecte_jee;
    }

    public void setScore_technical_lead__architecte_jee(float score_technical_lead__architecte_jee) {
        this.score_technical_lead__architecte_jee = score_technical_lead__architecte_jee;
    }

    public float getScore_fullstack_js() {
        return score_fullstack_js;
    }

    public void setScore_fullstack_js(float score_fullstack_js) {
        this.score_fullstack_js = score_fullstack_js;
    }

    public float getScore_java_jee() {
        return score_java_jee;
    }

    public void setScore_java_jee(float score_java_jee) {
        this.score_java_jee = score_java_jee;
    }

    public float getScore_php_symfony() {
        return score_php_symfony;
    }

    public void setScore_php_symfony(float score_php_symfony) {
        this.score_php_symfony = score_php_symfony;
    }

    public float getScore_drupal() {
        return score_drupal;
    }

    public void setScore_drupal(float score_drupal) {
        this.score_drupal = score_drupal;
    }

    public float getScore_product_owner() {
        return score_product_owner;
    }

    public void setScore_product_owner(float score_product_owner) {
        this.score_product_owner = score_product_owner;
    }

    public String getFinal_best_profile() {
        return final_best_profile;
    }

    public void setFinal_best_profile(String final_best_profile) {
        this.final_best_profile = final_best_profile;
    }

    public float getFinal_best_profile_score() {
        return final_best_profile_score;
    }

    public void setFinal_best_profile_score(float final_best_profile_score) {
        this.final_best_profile_score = final_best_profile_score;
    }

    public float getFinal_best_profile_mean_skills_duration() {
        return final_best_profile_mean_skills_duration;
    }

    public void setFinal_best_profile_mean_skills_duration(float final_best_profile_mean_skills_duration) {
        this.final_best_profile_mean_skills_duration = final_best_profile_mean_skills_duration;
    }

    public int getFinal_best_profile_categ_seniority() {
        return final_best_profile_categ_seniority;
    }

    public void setFinal_best_profile_categ_seniority(int final_best_profile_categ_seniority) {
        this.final_best_profile_categ_seniority = final_best_profile_categ_seniority;
    }
}
