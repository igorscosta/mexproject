package org.aksw.mex.log4mex.core;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.aksw.mex.log4mex.algo.AlgorithmVO;
import org.aksw.mex.log4mex.algo.ImplementationVO;
import org.aksw.mex.util.MEXConstant;
import org.aksw.mex.util.MEXController;
import org.aksw.mex.util.MEXEnum;
import org.aksw.mex.util.ontology.mex.MEXALGO_10;
import org.aksw.mex.util.ontology.mex.MEXCORE_10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by esteves on 25.06.15.
 */
public class ExperimentConfigurationVO {

    private String                     _id;
    private String                     _description;
    private Integer                    _seq = 0;

    private ModelVO                    _model;
    private PhaseVO                    _phase;
    private SamplingMethodVO           _sampling;
    private HardwareConfigurationVO    _hard;
    private DataSetVO                  _ds;
    private ImplementationVO           _implementation;

    private List<Execution>            _executions;
    private List<FeatureVO>            _features;
    private List<AlgorithmVO>          _algorithms;

    private static final Logger        LOGGER = LoggerFactory.getLogger(ExperimentConfigurationVO.class);

    /************************************ constructors ************************************/

    public ExperimentConfigurationVO(String id, String description) {
        this._id = id;
        this._description = description;
        this._executions = new ArrayList<>();
        this._features = new ArrayList<>();
        this._algorithms = new ArrayList<>();
        this._ds = new DataSetVO();
    }

    public ExperimentConfigurationVO(String id) {
        this._id = id;
        this._executions = new ArrayList<>();
        this._features = new ArrayList<>();
        this._algorithms = new ArrayList<>();
        this._ds = new DataSetVO();
    }

    public ExperimentConfigurationVO(String id, Integer nextid) {
        this._id = id;
        this._seq = nextid;
        this._executions = new ArrayList<>();
        this._features = new ArrayList<>();
        this._algorithms = new ArrayList<>();
        this._ds = new DataSetVO();
    }

    /************************************ getters ************************************/

    public String getId() {
        return _id;
    }

    public String getDescription() {
        return _description;
    }

    /* complex objects */

    public ModelVO Model() {
        if (this._model == null){
            this._model = new ModelVO();
        }
        return this._model;
    }

    public PhaseVO Phase() {
        if (this._phase == null){
            this._phase = new PhaseVO(MEXEnum.EnumPhases.TEST); //this is the default one
        }
        return this._phase;
    }

    public SamplingMethodVO SamplingMethod(){
        try {
            if (this._sampling == null) {
                this.addSamplingMethod(MEXEnum.EnumSamplingMethods.HOLDOUT, 1);
            }
            return this._sampling;
        }
        catch (Exception e){
            return null;
        }
    }

    public HardwareConfigurationVO HardwareConfiguration() {
        try {
            if (this._hard == null) {
                this.addHardwareConfiguration("", MEXEnum.EnumProcessors.NOT_INFORMED, MEXEnum.EnumRAM.NOT_INFORMED, "", MEXEnum.EnumCaches.NOT_INFORMED);
            }
            return this._hard;
        }
        catch (Exception e){
            return null;
        }
    }

    public DataSetVO DataSet() {
        if (_ds == null) {
            this._ds = new DataSetVO();
        }
        return this._ds;
    }

    public AlgorithmVO Algorithm(MEXEnum.EnumAlgorithms algo){
        if (this._algorithms == null) {
            this._algorithms = new ArrayList<>();}

        AlgorithmVO ret  = null;
        try {
            Collection<AlgorithmVO> t = Collections2.filter(this._algorithms, p -> p.getClassName().equals(algo.name()));
            if (t != null && t.size() > 0){
                ret = Iterables.get(t, 0);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return ret;
    }

    public ImplementationVO Implementation() {
        try {
            if (this._implementation == null) {
                this.addImplementation(MEXEnum.EnumImplementations.NOT_INFORMED, "");
            }
            return this._implementation;
        }
        catch (Exception e){
            return null;
        }
    }

    public FeatureVO getFeature(Integer index){
        return this._features.get(index);
    }

    public List<AlgorithmVO> getAlgorithms(){
        return this._algorithms;
    }

    public List<AlgorithmVO> getAlgorithms(String id){
        return this._algorithms;
    }

    public List<FeatureVO> getFeatures(){
        return this._features;
    }

    public List<Execution> getExecutions(){
        return this._executions;
    }

    public Execution Execution(String id){
        Execution ret  = null;
        try {
            Collection<Execution> t = Collections2.filter(this._executions, p -> p._id.equals(id));
            if (t != null && t.size() >0){ret = Iterables.get(t, 0);}
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return ret;
    }

    private ExecutionSetVO ExecutionOverall(String id){

        ExecutionSetVO r = null;
        try {
            Collection<Execution> t = Collections2.filter(this._executions, p -> p._id.equals(id));
            if (t != null && t.size() >0){
                if (Iterables.get(t, 0) instanceof ExecutionSetVO) {
                    r = (ExecutionSetVO) Iterables.get(t, 0);}
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return r;
    }

    /************************************ setters ************************************/

    public void setId(String _id) {
        this._id = _id;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public void setExecutionStartTime(String executionId, Date value){
        try {
            Collection<Execution> t = Collections2.filter(this._executions, p -> p._id.equals(executionId));
            if (t != null && t.size() >0){Iterables.get(t, 0).setStartDate(value);}
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void setExecutionEndTime(String executionId, Date value){
        try {
            Collection<Execution> t = Collections2.filter(this._executions, p -> p._id.equals(executionId));
            if (t != null && t.size() > 0){Iterables.get(t, 0).setEndDate(value);}
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    /* complex objects */

    public void setModel(ModelVO model) {
        this._model = model;
    }

    public void setPhase(PhaseVO phase) {
        this._phase = phase;
    }

    public void setSamplingMethod(SamplingMethodVO value) {
        this._sampling = value;
    }

    public void setHardwareConfiguration(HardwareConfigurationVO value) {
        this._hard = value;
    }

    public void setDataSet(DataSetVO value) {
        this._ds = value;
    }

    public void setSamplingMethod(String classname, MEXEnum.EnumSamplingMethods name) {
        this._sampling = new SamplingMethodVO(classname, name) ;
    }

    public void setExecutionId(Integer index, String id){
        this._executions.get(index)._id = id;
    }

    /************************************ functions ************************************/

    //public boolean addExecution(Execution param){
    //    return _executions.add(param);
    //}

    public boolean removeExecution(Execution param){
        return _executions.remove(param);
    }

    private void addExecutionOverall(String expID, String id, MEXEnum.EnumPhases phase){
        this._executions.add(new ExecutionSetVO(this, id, new PhaseVO(phase)));
    }

    public String addExecution(MEXEnum.EnumExecutionsType type, MEXEnum.EnumPhases phase) throws Exception{

        Integer total;
        String execCode;

        total = this._executions.size() + 1;

        if (type.toString().equals(MEXEnum.EnumExecutionsType.SINGLE.toString())) {
            this._executions.add(new ExecutionIndividualVO(this, "C" + this._seq.toString() + "_" + MEXConstant.DEFAULT_EXEC_ID + String.valueOf(total), new PhaseVO(phase)));
        }
        if (type.toString().equals(MEXEnum.EnumExecutionsType.OVERALL.toString())) {
            this._executions.add(new ExecutionSetVO(this, "C" + this._seq.toString() + "_" + MEXConstant.DEFAULT_EXEC_ID + String.valueOf(total), new PhaseVO(phase)));
        }
        execCode = "C" + this._seq.toString() + "_" + MEXConstant.DEFAULT_EXEC_ID + total.toString();
        LOGGER.debug(execCode);
        return execCode;

    }

    private void _addFeatures(String[] featuresName){
        if (this._features == null) {
            this._features = new ArrayList<>();}

        int size = featuresName.length;
        for (int i=0; i<size; i++)
        {
            String feature = featuresName[i];
            try {
                Collection<FeatureVO> t = Collections2.filter(this._features, p -> p.getId().equals(feature));
                if (t != null && t.size() > 0){throw new Exception("Features already assigned");}
                else {
                    this._features.add(new FeatureVO(String.valueOf(this._features.size()+1), feature));
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public void addFeature(String featureName){
       _addFeatures(new String[]{featureName});
    }

    public void addFeature(String[] featuresName){
        _addFeatures(featuresName);
    }

    public void addSamplingMethod(MEXEnum.EnumSamplingMethods sm, Double train, Double test) throws Exception{

        try{

            if (this._sampling == null) {
                String individualName = MEXCORE_10.ClasseTypes.SAMPLING_METHOD.toLowerCase() +
                        String.valueOf(MEXController.getInstance().getNumberOfSamplingMethods() + 1);

                this._sampling = new SamplingMethodVO(individualName,sm, train, test);

                MEXController.getInstance().addSamplingMethodCounter();
            }



        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void addSamplingMethod(MEXEnum.EnumSamplingMethods sm, Integer folds) throws Exception{

        try{

            if (this._sampling == null) {
                String individualName = MEXCORE_10.ClasseTypes.SAMPLING_METHOD.toLowerCase() +
                        String.valueOf(MEXController.getInstance().getNumberOfSamplingMethods() + 1);

                this._sampling = new SamplingMethodVO(individualName,sm);
                this._sampling.setFolds(folds);
                this._sampling.setTrainSize((double)folds - 1);
                this._sampling.setTestSize((double)1);

                MEXController.getInstance().addSamplingMethodCounter();
            }



        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void addModel(String id, String description, Date date){
        if (this._model == null){
            this._model = new ModelVO();
        }
        this._model.setDate(date);
        this._model.setDescription(description);
        this._model.setId(id);
    }

    public void addHardwareConfiguration(String os, MEXEnum.EnumProcessors cpu, MEXEnum.EnumRAM mb, String hd, MEXEnum.EnumCaches cache){
        if (this._hard == null){
            this._hard = new HardwareConfigurationVO();
        }
        this._hard.setCache(cache);
        this._hard.setOperationalSystem(os);
        this._hard.setCPU(cpu);
        this._hard.setMemory(mb);
        this._hard.setHD(hd);

    }

    public void addDataSet(String URI, String description, String name){
        if (this._ds == null){
            this._ds = new DataSetVO();
        }
        this._ds.setDescription(description);
        this._ds.setName(name);
        this._ds.setURI(URI);
    }

    public void addImplementation(MEXEnum.EnumImplementations name, String version){
        if (this._implementation == null){
            this._implementation = new ImplementationVO();
        }
        this._implementation.setRevision(version);
        this._implementation.setName(name.name());
    }

    private AlgorithmVO _addAlgorithm(MEXEnum.EnumAlgorithms algorithmClass, String id) throws Exception{

        AlgorithmVO algo = null;
        try{
            if (this._algorithms == null) {
                this._algorithms = new ArrayList<>();}

            String individualName = MEXALGO_10.ClasseTypes.ALGORITHM.toLowerCase() +
                    String.valueOf(MEXController.getInstance().getNumberOfAlgorithms() + 1);

            if (id == "") id = individualName;

            algo = new AlgorithmVO(algorithmClass.name().toString(), individualName, id);
            this._algorithms.add(algo);

            MEXController.getInstance().addAlgorithmCounter();

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return algo;

    }

    public AlgorithmVO addAlgorithm(MEXEnum.EnumAlgorithms algorithmClass, String id) throws Exception{
        return _addAlgorithm(algorithmClass, id);
    }

    public AlgorithmVO addAlgorithm(MEXEnum.EnumAlgorithms algorithmClass) throws Exception{
        return _addAlgorithm(algorithmClass, "");
    }

}
