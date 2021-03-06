# Generated by Django 2.0.6 on 2018-06-09 16:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('animais', '0003_auto_20180609_1309'),
    ]

    operations = [
        migrations.AlterField(
            model_name='animal',
            name='pelagem',
            field=models.CharField(blank=True, choices=[('1', 'Curta'), ('2', 'Longa')], default=None, max_length=1),
        ),
        migrations.AlterField(
            model_name='animal',
            name='sexo',
            field=models.CharField(blank=True, choices=[('1', 'Fêmea'), ('2', 'Macho'), ('3', 'Não identificado')], default=None, max_length=1),
        ),
        migrations.AlterField(
            model_name='cao',
            name='porte',
            field=models.CharField(choices=[('1', 'Pequeno'), ('2', 'Médio'), ('3', 'Grande')], default=None, max_length=1),
        ),
    ]
