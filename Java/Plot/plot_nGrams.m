function [] = plot_nGrammi_java()
%% Plot bigrammi

results_2grams_seq = csvread('./csv_data/seq_2grams.csv');
results_2grams_seq = results_2grams_seq/1000;

results_2grams_parallel_2th = csvread('./csv_data/parallel_2grams_2thread.csv');
results_2grams_parallel_2th = results_2grams_parallel_2th/1000;

results_2grams_parallel_4th = csvread('./csv_data/parallel_2grams_4thread.csv');
results_2grams_parallel_4th = results_2grams_parallel_4th/1000;

results_2grams_parallel_8th = csvread('./csv_data/parallel_2grams_8thread.csv');
results_2grams_parallel_8th = results_2grams_parallel_8th/1000;

figure(1);
x_axis = log10([50,500,10000,50000,100000,150000]);
plot(x_axis,results_2grams_seq,'.-','MarkerSize',20,'LineWidth',2);
title('2-grams Java')
xticks(x_axis);
xticklabels({'50Kb','500Kb','10Mb','50Mb','100Mb','150Mb'});
xlabel('Text dimension');
ylabel('Time (s)');
hold on;
plot(x_axis,results_2grams_parallel_2th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,results_2grams_parallel_4th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,results_2grams_parallel_8th,'.-','MarkerSize',20,'LineWidth',2);
legend({'Sequential','2 thread','4 thread','8 thread'},'FontSize',12);
%% Pot Trigrammi

results_3grams_seq = csvread('./csv_data/seq_3grams.csv');
results_3grams_seq = results_3grams_seq/1000;

parallel_3grams_2th = csvread('./csv_data/parallel_3grams_2thread.csv');
parallel_3grams_2th = parallel_3grams_2th/1000;

parallel_3grams_4th = csvread('./csv_data/parallel_3grams_4thread.csv');
parallel_3grams_4th = parallel_3grams_4th/1000;

parallel_3grams_8th = csvread('./csv_data/parallel_3grams_8thread.csv');
parallel_3grams_8th = parallel_3grams_8th/1000;

figure(2);
plot(x_axis,results_3grams_seq,'.-','MarkerSize',20,'LineWidth',2);
title('3-grams Java')
xticks(x_axis);
xticklabels({'50Kb','500Kb','10Mb','50Mb','100Mb','150Mb'});
xlabel('Text dimension');
ylabel('Time (s)');
hold on;
plot(x_axis,parallel_3grams_2th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,parallel_3grams_4th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,parallel_3grams_8th,'.-','MarkerSize',20,'LineWidth',2);

legend({'Sequential','2 thread','4 thread','8 thread'},'FontSize',12);
%% Speedup Trigrammi

speed_t_2th = results_3grams_seq./parallel_3grams_2th;
speed_t_4th = results_3grams_seq./parallel_3grams_4th;
speed_t_8th = results_3grams_seq./parallel_3grams_8th;


figure(3);
plot(x_axis,speed_t_2th,'.-','MarkerSize',20,'LineWidth',2);
title('Speed Up Trigrams')
xticks(x_axis);
xticklabels({'50Kb','500Kb','10Mb','50Mb','100Mb','150Mb'});
xlabel('Text dimension');
ylabel('Speed Up');
hold on;
plot(x_axis,speed_t_4th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,speed_t_8th,'.-','MarkerSize',20,'LineWidth',2);

legend({'2 thread','4 thread','8 thread'},'FontSize',12);
%% Speedup Bigrammi

speed_b_2th = results_2grams_seq./results_2grams_parallel_2th;
speed_b_4th = results_2grams_seq./results_2grams_parallel_4th;
speed_b_8th = results_2grams_seq./results_2grams_parallel_8th;


figure(4);
plot(x_axis,speed_b_2th,'.-','MarkerSize',20,'LineWidth',2);
title('Speed Up Bigrams')
xticks(x_axis);
xticklabels({'50Kb','500Kb','10Mb','50Mb','100Mb','150Mb'});
xlabel('Text dimension');
ylabel('Speed Up');
hold on;
plot(x_axis,speed_b_4th,'.-','MarkerSize',20,'LineWidth',2);
plot(x_axis,speed_b_8th,'.-','MarkerSize',20,'LineWidth',2);
legend({'2 thread','4 thread','8 thread'},'FontSize',12);

end

