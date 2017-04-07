package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job aJob=jobData.findById(id);
        /*
        findbyid
        model.addAttribute("jobdetails",jobfielddata.findall)
         */
        model.addAttribute("aJob", aJob);
        return "job-detail";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors,
                      @RequestParam("name") String name,
                      @RequestParam("employerId") int employerId,
                      @RequestParam("location") int locationId,
                      @RequestParam("skill") int skillId,
                        @RequestParam("positiontypes") int positiontypeId) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()){
            model.addAttribute(jobForm);
            return "new-job";
        }
        else{
            Employer employer = jobData.getEmployers().findById(employerId);
            Location location = jobData.getLocations().findById(locationId);
            CoreCompetency skill = jobData.getCoreCompetencies().findById(skillId);
            PositionType positiontype= jobData.getPositionTypes().findById(positiontypeId);

            Job newJob=new Job(name, employer,location, positiontype,skill);
            jobData.add(newJob);
            int id= newJob.getId();

        return "redirect:?id="+ id;
        }
    }
}
