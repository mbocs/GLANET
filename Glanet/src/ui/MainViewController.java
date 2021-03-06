package ui;

import javax.swing.JPanel;

import ui.MainView.MainViewDelegate;
import common.CommandLineArguments;
import common.Commons;

public class MainViewController extends ViewController implements MainViewDelegate {
	
	private MainView mainView;
	private Thread runnerThread;
	private GlanetRunner runner;
	
	public MainViewController( JPanel contentPanel) {
		super(contentPanel);
		
		loadView();
	}

	@Override
	public void loadView() {
		
		if( mainView != null){
			contentPanel.remove(mainView);
		}
		
		mainView = new MainView();
		mainView.setDelegate( this);
		contentPanel.add(mainView);
	}

	@Override
	public void presentViewController(ViewController viewController) {

		contentPanel.removeAll();
		contentPanel.invalidate();
		viewController.loadView();
		contentPanel.revalidate();
		
	}

	@Override
	public void dismissViewController() {
		contentPanel.removeAll();
		contentPanel.add(mainView);
	}
	
	@Override
	public void startRunActionsWithOptions( String inputFileName, 
			   String outputFolder,
			   String inputFileFormat,
			   String numberOfBases,
			   String enrichmentEnabled,
			   String generateRandomDataMode,
			   String multipleTestingChoice,
			   String bonferoniCorrectionSignificanceLevel,
			   String falseDiscoveryRate,
			   String numberOfPermutations,
			   String dnaseEnrichment,
			   String histoneEnrichment,
			   String tfEnrihment,
			   String keggPathwayEnrichment,
			   String tfAndKeggPathwayEnrichment,
			   String cellLineBasedTfAndKeggPathwayEnrichment,
			   String regulatorySequenceAnalysisUsingRSAT,
			   String jobName,
			   String writeGeneratedRandomDataMode,
			   String writePermutationBasedandParametricBasedAnnotationResultMode,
			   String writePermutationBasedAnnotationResultMode,
			   String numberOfPermutationsInEachRun,
			   String userDefinedGeneSetEnrichment,
			   String userDefinedGeneSetInputFile,
			   String userDefinedGeneSetGeneInformation,
			   String userDefinedGeneSetName,
			   String userDefinedGeneSetDescription,
			   String userDefinedLibraryEnrichment,
			   String userDefinedLibraryInputFile,
			   String userDefinedLibraryDataFormat,
			   String[] cellLinesToBeConsidered) {
		
		String[] args = new String[CommandLineArguments.NumberOfArguments.value() + cellLinesToBeConsidered.length];
		
		args[CommandLineArguments.InputFileNameWithFolder.value()] = inputFileName;
		args[CommandLineArguments.GlanetFolder.value()] = outputFolder;
		args[CommandLineArguments.InputFileDataFormat.value()] = inputFileFormat;
		args[CommandLineArguments.NumberOfBases.value()] = numberOfBases;
		args[CommandLineArguments.PerformEnrichment.value()] = enrichmentEnabled;
		args[CommandLineArguments.GenerateRandomDataMode.value()] = generateRandomDataMode;
		args[CommandLineArguments.MultipleTesting.value()] = multipleTestingChoice;
		args[CommandLineArguments.SignificanceCriteria.value()] = bonferoniCorrectionSignificanceLevel;
		args[CommandLineArguments.FalseDiscoveryRate.value()] = falseDiscoveryRate;
		args[CommandLineArguments.NumberOfPermutation.value()] = numberOfPermutations;
		args[CommandLineArguments.DnaseAnnotation.value()] = dnaseEnrichment;
		args[CommandLineArguments.HistoneAnnotation.value()] = histoneEnrichment;
		args[CommandLineArguments.TfAnnotation.value()] = tfEnrihment;
		args[CommandLineArguments.KeggPathwayAnnotation.value()] = keggPathwayEnrichment;
		args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()] = tfAndKeggPathwayEnrichment;
		args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()] = cellLineBasedTfAndKeggPathwayEnrichment;
		args[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()] = regulatorySequenceAnalysisUsingRSAT;
		args[CommandLineArguments.JobName.value()] = jobName;
		args[CommandLineArguments.WriteGeneratedRandomDataMode.value()] = writeGeneratedRandomDataMode;
		args[CommandLineArguments.WritePermutationBasedandParametricBasedAnnotationResultMode.value()] = writePermutationBasedandParametricBasedAnnotationResultMode;
		args[CommandLineArguments.WritePermutationBasedAnnotationResultMode.value()] = writePermutationBasedAnnotationResultMode;
		args[CommandLineArguments.NumberOfPerInEachRun.value()] = numberOfPermutationsInEachRun;
		args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()] = userDefinedGeneSetEnrichment;
		args[CommandLineArguments.UserDefinedGeneSetInput.value()] = userDefinedGeneSetInputFile;
		args[CommandLineArguments.UserDefinedGeneSetGeneInformation.value()] = userDefinedGeneSetGeneInformation;
		args[CommandLineArguments.UserDefinedGeneSetName.value()] = userDefinedGeneSetName;
		args[CommandLineArguments.UserDefinedGeneSetDescriptionFile.value()] = userDefinedGeneSetDescription;
		args[CommandLineArguments.UserDefinedLibraryAnnotation.value()] = userDefinedLibraryEnrichment;
		args[CommandLineArguments.UserDefinedLibraryInput.value()] = userDefinedLibraryInputFile;
		args[CommandLineArguments.UserDefinedLibraryDataFormat.value()] = userDefinedLibraryDataFormat;
		
		//filling the rest with selected cell lines. 
		for( int i = CommandLineArguments.NumberOfArguments.value(); i < args.length; i++)
			args[i] = cellLinesToBeConsidered[i-Commons.NUMBER_OF_PROGRAM_RUNTIME_ARGUMENTS];
		
		for( int i = 0; i < args.length; i++)
			System.out.println(args[i]);
		
		runner = new GlanetRunner();
		
		GlanetRunner.setArgs( args);
		GlanetRunner.setMainView( mainView);
		
		runnerThread = new Thread(runner);
		runnerThread.start();
	}
	
	@Override
	public void stopCurrentProcess() {
		
		runnerThread.interrupt();
	}
}