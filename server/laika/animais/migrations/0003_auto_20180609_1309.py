# Generated by Django 2.0.6 on 2018-06-09 16:09

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('animais', '0002_auto_20180609_1219'),
    ]

    operations = [
        migrations.AlterField(
            model_name='animal',
            name='idade',
            field=models.DateField(),
        ),
    ]
