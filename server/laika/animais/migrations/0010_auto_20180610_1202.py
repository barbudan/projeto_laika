# Generated by Django 2.0.6 on 2018-06-10 15:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('animais', '0009_auto_20180610_1201'),
    ]

    operations = [
        migrations.AlterField(
            model_name='animal',
            name='peso',
            field=models.IntegerField(blank=True, null=True),
        ),
    ]
